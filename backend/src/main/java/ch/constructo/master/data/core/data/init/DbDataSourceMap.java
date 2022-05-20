package ch.constructo.master.data.core.data.init;

import ch.constructo.master.data.core.data.config.BasePersistenceConfig;
import ch.constructo.master.data.core.data.config.DataSourceMapDescriptor;
import ch.constructo.master.data.core.data.config.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import static java.lang.String.format;

@Component
public class DbDataSourceMap implements DataSourceMapDescriptor {

  private final transient Logger log = LoggerFactory.getLogger(getClass());

  private Map<Object, Object> dataSourceMap = new HashMap<>();
  private Map<String, String> databaseMap= new HashMap<>();


  private Properties masterProperties = new Properties();

  public DbDataSourceMap() {
  }

  public Map<Object, Object> getDataSourceMap() {

    if(dataSourceMap.isEmpty()){
      initializeDataSource();
    }
    return dataSourceMap;
  }

  @Override
  public void removeSource(String session, String databaseName) {

  }

  public Map<String, String> getDatabaseMap() {
    return databaseMap;
  }

  public void setDatabaseMap(Map<String, String> databaseMap) {
    this.databaseMap = databaseMap;
  }

  private void initializeDataSource(){

    String dir = "/src/test/resources";

    Resource masterdataConfig = new FileSystemResource(dir + "/masterdata.properties");
    if (!masterdataConfig.exists()) {
      masterdataConfig = new ClassPathResource("/masterdata.properties");
      log.info(format("file '%s' exists: '%s'", masterdataConfig.getFilename(), masterdataConfig.exists()));

      if (masterdataConfig.exists()) {

        try {
          masterProperties.load(masterdataConfig.getInputStream());
        } catch (IOException e) {
          throw new IllegalStateException("could not open base " + masterdataConfig.getFilename(), e);
        }
        Connection conn = null;
        Statement stmt = null;
        try {

          Class.forName(masterProperties.getProperty("master.jdbc.driverClassName"));
          conn = DriverManager.getConnection(masterProperties.getProperty("master.jdbc.url"), masterProperties.getProperty("master.jdbc.username"), masterProperties.getProperty("master.jdbc.password"));

          stmt = conn.createStatement();

          initMasterDb(dir, masterProperties, stmt); // only for TestCases and DEV

        } catch (Exception e){
          log.error("could not connect master data", e);
          throw new RuntimeException("could not connect master data", e);
        } finally {
          try{
            stmt.close();
          } catch (Exception e){
            // do nothing
          } finally {
            try {
              conn.close();
            } catch (SQLException e) {
              // do nothing
            }
          }
        }
      } else {
        throw new IllegalStateException("masterdata.properties not available in directory: " + dir);
      }
    }
  }

  private void initMasterDb(String tomcatDir, Properties properties, Statement stmt) throws IOException {
    boolean initFails = false;
    try {
      String initDb = properties.getProperty("master.init-db");

        String scriptResource = properties.getProperty("master.init-script");
        Resource masterDataInitScript = null;
        if (scriptResource.startsWith("classpath:")) {
          masterDataInitScript = new ClassPathResource(scriptResource.substring(10));
        } else if (scriptResource.startsWith("file:")) {
          masterDataInitScript = new FileSystemResource(scriptResource.substring(5));
        } else {
          masterDataInitScript = new FileSystemResource(tomcatDir + "/" + scriptResource);
        }

        List<String> initStatements = new ArrayList<>();
        Scanner scanner = new Scanner(masterDataInitScript.getInputStream());
        while(scanner.hasNextLine()){
          String line = scanner.nextLine();
          initStatements.add(line);
        }

        for(String sql : initStatements){
          if(sql.isEmpty() || sql.startsWith("--") || sql.startsWith("\\u001a")){
            continue;
          }
          log.info(format("init db: %s", sql));
          if(sql.endsWith(";")){
            sql = sql.substring(0, sql.length()-1);
          }
          stmt.execute(sql);

      }

    }catch (SQLException e){
      if(!e.getSQLState().equalsIgnoreCase("42X01")){
        log.error("could not initialize master data", e);
        initFails = true;
        throw new RuntimeException("could not initialize master data", e);
      }
    } finally {
      if(!initFails){
        BasePersistenceConfig.getAlreadyInitialized().put(properties.getProperty("master.jdbc.url"), Boolean.TRUE);
      }
    }
  }

  private DatabaseProperties prepareDatatbaseProperties(Resource rootConfig, String customerId, String databaseName) {
    boolean runsInTest = false;
    if(masterProperties.size()==0){
      throw new RuntimeException("no masterdata properties are available!");
    }
    Connection conn = null;
    Statement stmt = null;
    try {

      Class.forName(masterProperties.getProperty("master.jdbc.driverClassName"));
      conn = DriverManager.getConnection(masterProperties.getProperty("master.jdbc.url"), masterProperties.getProperty("master.jdbc.username"), masterProperties.getProperty("master.jdbc.password"));

      stmt = conn.createStatement();

      String sql = format("Select dbServerId, maximumPoolSize, minimumIdle  from Customer where customerId = '%s'", customerId);
      ResultSet resultSet = stmt.executeQuery(sql);
      if(!resultSet.next()){
        throw new RuntimeException(format("referenced for customerId=%s / databaseName=%s not exist!", customerId, databaseName));
      }
      Long dbServerId = resultSet.getLong("dbServerId");

      DatabaseProperties databaseProperties;
      databaseProperties = new DatabaseProperties(rootConfig);

      Integer maximumPoolSize = resultSet.getInt("maximumPoolSize");
      Integer minimumIdle = resultSet.getInt("minimumIdle");

      databaseProperties.setMaximumPoolSize(maximumPoolSize);
      databaseProperties.setMinimumIdle(minimumIdle);

      sql = format("Select *  from DbServer where id = %s", dbServerId);
      resultSet = stmt.executeQuery(sql);
      if (resultSet.next()) {
        //Retrieve by column name
        String host = resultSet.getString("host");
        Integer port = resultSet.getInt("port");
        DbServerTyp dbServerTyp = DbServerTyp.values()[resultSet.getInt("dbServerTyp")];
        String url = null;
        switch (dbServerTyp){
          case SQL_SERVER:
            databaseProperties.setJdbcDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            url = format("jdbc:sqlserver://%s:%s;databaseName=%s", host, port, databaseName);
            databaseProperties.setHibernateDialect("org.hibernate.dialect.SQLServer2012Dialect");
            break;
          case DERBY:
            databaseProperties.setJdbcDriver("org.apache.derby.jdbc.EmbeddedDriver");
            if(runsInTest){
              url = format("jdbc:derby:memory:%s;create=true", databaseName);
            } else {
              throw new RuntimeException(format("Derby is not supported outside internal test cases! for dbServiceId=%s", dbServerId));
            }
            databaseProperties.setHibernateDialect("org.hibernate.dialect.DerbyTenSevenDialect");
            break;
          default:
            throw new RuntimeException(format("could not handle db server typ for dbServiceId=%s", dbServerId));
        }
        databaseProperties.setJdbcUrl(url);
        databaseProperties.setName(databaseName);


        String username = resultSet.getString("username");
        databaseProperties.setJdbcUser(username);

        String password = resultSet.getString("password");
        databaseProperties.setJdbcPassword(password);;

        if(databaseName != null && !databaseName.isEmpty()){
          databaseMap.put(customerId, databaseName);
          log.info(format("databaseMap map entry for customerId/databaseName '%s'/'%s' added", customerId, databaseName));
        }
      } else  {
        // use/read config from sidac.properties
        databaseProperties.setJdbcUrl(databaseProperties.getJdbcUrl().replace("CONSTRUCTO_DB_NAME", databaseName));
        databaseProperties.setName(databaseName);
      }
      return databaseProperties;

    } catch (Exception e){
      log.error("could not connect master data", e);
      throw new RuntimeException("could not connect master data", e);
    } finally {
      try{
        stmt.close();
      } catch (Exception e){
        // do nothing
      } finally {
        try {
          conn.close();
        } catch (SQLException e) {
          // do nothing
        }
      }
    }
  }

  public enum DbServerTyp {
    SQL_SERVER,
    DERBY,
  }
}
