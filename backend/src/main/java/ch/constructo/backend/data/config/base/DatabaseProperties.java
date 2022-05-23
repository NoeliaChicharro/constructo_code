package ch.constructo.backend.data.config.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
  private final transient Logger log = LoggerFactory.getLogger(DatabaseProperties.class);

  private Integer maximumPoolSize;
  private Integer minimumIdle;

  private Integer mandantenMaximumPoolSize;
  private Integer mandantenMinimumIdle;

  /**
   * <p>Constructor for DatabaseProperties.</p>
   *
   * @param databaseProperties a {@link Resource} object.
   */
  public DatabaseProperties(Resource databaseProperties) {
    Properties properties = new Properties();
    try {
      properties.load(databaseProperties.getInputStream());
    } catch (IOException e) {
      throw new IllegalStateException("could not open base " + databaseProperties.getFilename(), e);
    }

    name = properties.getProperty("constructo.name");
    jndiEnabled = Boolean.parseBoolean(properties.getProperty("constructo.use-jndi"));
    jndiName = properties.getProperty("constructo.jndiName");
    jdbcDriver = properties.getProperty("constructo.jdbc.driverClassName");
    jdbcUrl = properties.getProperty("constructo.jdbc.url");
    jdbcUser = properties.getProperty("constructo.jdbc.username");
    jdbcPassword = properties.getProperty("constructo.jdbc.password");
    hibernateDialect = properties.getProperty("constructo.hibernate.dialect");
    initDatabase = Boolean.parseBoolean(properties.getProperty("constructo.init-db"));

    try{
      maximumPoolSize = Integer.parseInt(properties.getProperty("constructo.maximumPoolSize"));
      minimumIdle = Integer.parseInt(properties.getProperty("constructo.minimumIdle"));
    }catch (Exception e){

    }

    String script = properties.getProperty("constructo.init-script");
    if (script.startsWith("classpath:")) {
      initScript = new ClassPathResource(script.substring(10));
    } else if (script.startsWith("file:")) {
      initScript = new FileSystemResource(script.substring(5));
    } else {
      initScript = new FileSystemResource(script.substring(5));
    }

  }

  private String name;

  private boolean jndiEnabled;

  private String jndiName;

  private String jdbcDriver;

  private String jdbcUrl;

  private String jdbcUser;

  private String jdbcPassword;

  private boolean initDatabase;

  private Resource initScript;

  private String hibernateDialect;


  /**
   * <p>Getter for the field <code>name</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getName() {
    return name;
  }

  /**
   * <p>isInitDatabase.</p>
   *
   * @return a boolean.
   */
  public boolean isInitDatabase() {
    return initDatabase;
  }

  /**
   * <p>Getter for the field <code>initScript</code>.</p>
   *
   * @return a {@link Resource} object.
   */
  public Resource getInitScript() {
    return initScript;
  }

  /**
   * <p>Getter for the field <code>jdbcDriver</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getJdbcDriver() {
    return jdbcDriver;
  }

  /**
   * <p>Getter for the field <code>jdbcPassword</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getJdbcPassword() {
    return jdbcPassword;
  }

  /**
   * <p>Getter for the field <code>jdbcUrl</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getJdbcUrl() {
    return jdbcUrl;
  }

  /**
   * <p>Getter for the field <code>jdbcUser</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getJdbcUser() {
    return jdbcUser;
  }

  /**
   * <p>isJndiEnabled.</p>
   *
   * @return a boolean.
   */
  public boolean isJndiEnabled() {
    return jndiEnabled;
  }

  /**
   * <p>Getter for the field <code>jndiName</code>.</p>
   *
   * @return a {@link String} object.
   */
  public String getJndiName() {
    return jndiName;
  }

  public String getHibernateDialect() {
    return hibernateDialect;
  }

  public void setJdbcUrl(String jdbcUser) {
    this.jdbcUrl = jdbcUser;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setJndiEnabled(boolean jndiEnabled) {
    this.jndiEnabled = jndiEnabled;
  }

  public void setJndiName(String jndiName) {
    this.jndiName = jndiName;
  }

  public void setJdbcDriver(String jdbcDriver) {
    this.jdbcDriver = jdbcDriver;
  }

  public void setJdbcUser(String jdbcUser) {
    this.jdbcUser = jdbcUser;
  }

  public void setJdbcPassword(String jdbcPassword) {
    this.jdbcPassword = jdbcPassword;
  }

  public void setInitDatabase(boolean initDatabase) {
    this.initDatabase = initDatabase;
  }

  public void setHibernateDialect(String hibernateDialect) {
    this.hibernateDialect = hibernateDialect;
  }

  public void setMaximumPoolSize(Integer maximumPoolSize) {
    this.maximumPoolSize = maximumPoolSize;
  }

  public Integer getMaximumPoolSize() {
    return maximumPoolSize;
  }

  public void setMinimumIdle(Integer minimumIdle) {
    this.minimumIdle = minimumIdle;
  }

  public Integer getMinimumIdle() {
    return minimumIdle;
  }

  public Integer getMandantenMaximumPoolSize() {
    return mandantenMaximumPoolSize;
  }

  public void setMandantenMaximumPoolSize(Integer mandantenMaximumPoolSize) {
    this.mandantenMaximumPoolSize = mandantenMaximumPoolSize;
  }

  public Integer getMandantenMinimumIdle() {
    return mandantenMinimumIdle;
  }

  public void setMandantenMinimumIdle(Integer mandantenMinimumIdle) {
    this.mandantenMinimumIdle = mandantenMinimumIdle;
  }
}
