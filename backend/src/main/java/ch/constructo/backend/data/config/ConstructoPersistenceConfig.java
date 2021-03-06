package ch.constructo.backend.data.config;


import ch.constructo.backend.data.config.base.BasePersistenceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement()
@EnableJpaRepositories(
    basePackages = {Constants.CH_CONSTRUCTO_MASTER_REPOSITORY_PACKAGE},
    entityManagerFactoryRef = Constants.CH_CONSTRUCTO_MASTER_EM,
    transactionManagerRef = Constants.CH_CONSTRUCTO_MASTER_DATA_TRX
)
@ComponentScan(basePackages = {
        Constants.CH_CONSTRUCTO_MASTER_SERVICES})
@EnableAspectJAutoProxy
public class ConstructoPersistenceConfig extends BasePersistenceConfig {

  @Value("${constructo.use-jndi:false}")
  private boolean jndiEnabled;

  @Value("${constructo.jndiName:java:comp/env/jdbc/<dbName>}")
  private String jndiName;

  @Value("${constructo.jdbc.driverClassName:com.microsoft.sqlserver.jdbc.SQLServerDriver}")
  private String jdbcDriver;

  @Value("${constructo.jdbc.url:jdbc:derby:memory:testdb;create=true}")
  private String jdbcUrl;

  @Value("${constructo.jdbc.username:APP}")
  private String jdbcUser;

  @Value("${constructo.jdbc.password:}")
  private String jdbcPassword;

  @Value("${constructo.init-db:false}")
  private boolean initDatabase;

  @Value("${constructo.init-script:initial-database.sql}")
  private Resource initScript;

  @Value("${constructo.hibernate.dialect:org.hibernate.dialect.SQLServer2012Dialect}")
  private String hibernateDialect;

  @Value("${constructo.hibernate.show_sql:false}")
  private String hibernateShowSql;

  @Value("${constructo.hibernate.hbm2ddl.auto:}")
  private String hibernateHBM2DDLauto;

  @Value("${constructo.maximumPoolSize:5}")
  private int maximumPoolSize;

  @Value("${constructo.minimumIdle:2}")
  private int minimumIdle;


  /**
   * <p>scanDomainPackages.</p>
   *
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] scanDomainPackages() {
    return new String[]{Constants.CH_CONSTRUCTO_MASTER_DOMAIN_PACKAGE};
  }

  /**
   * <p>transactionManager.</p>
   *
   * @return a {@link org.springframework.transaction.PlatformTransactionManager} object.
   */
  @Bean(name = Constants.CH_CONSTRUCTO_MASTER_DATA_TRX)
  public PlatformTransactionManager masterdataDataTrxManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(masterdataEM().getObject());
    return transactionManager;
  }

  /**
   * <p>constructoEM.</p>
   *
   * @return a {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean} object.
   */
  @Bean(name = Constants.CH_CONSTRUCTO_MASTER_EM)
  public LocalContainerEntityManagerFactoryBean masterdataEM() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(Boolean.FALSE);
    vendorAdapter.setShowSql(Boolean.valueOf(getHibernateShowSql()));

    factory.setDataSource(masterdataDS());
    factory.setJpaVendorAdapter(vendorAdapter);

    factory.setPackagesToScan(scanDomainPackages());

    factory.setJpaProperties(additionalJpaProperties());

    factory.setPersistenceUnitName(Constants.MASTER_PERSISTENCE_UNIT_NAME);
    factory.afterPropertiesSet();
    factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
    return factory;
  }

  /**
   * <p>constructoDS.</p>
   *
   * @return a {@link javax.sql.DataSource} object.
   */
  @Bean(name = Constants.CH_CONSTRUCTO_MASTER_DS)
  public DataSource masterdataDS() {
    return super.dataSource();
  }

  /**
   * <p>constructoSourceInitializer.</p>
   *
   * @param dataSource a {@link javax.sql.DataSource} object.
   * @return a {@link org.springframework.jdbc.datasource.init.DataSourceInitializer} object.
   */
  @Bean(name = "SourceInitializer")
  public DataSourceInitializer constructoSourceInitializer(@Qualifier(Constants.CH_CONSTRUCTO_MASTER_DS) DataSource dataSource) {
    return super.dataSourceInitializer(dataSource);
  }


  @Override
  protected void setInitDatabase(boolean initDatabase) {
    this.initDatabase = initDatabase;
  }

  /** {@inheritDoc} */
  @Override
  public String getHibernateDialect() {
    return hibernateDialect;
  }

  /** {@inheritDoc} */
  @Override
  public String getHibernateHBM2DDLauto() {
    return hibernateHBM2DDLauto;
  }

  /** {@inheritDoc} */
  @Override
  public String getHibernateShowSql() {
    return hibernateShowSql;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isInitDatabase() {
    return initDatabase;
  }

  /** {@inheritDoc} */
  @Override
  public Resource getInitScript() {
    return initScript;
  }

  /** {@inheritDoc} */
  @Override
  public String getJdbcDriver() {
    return jdbcDriver;
  }

  /** {@inheritDoc} */
  @Override
  public String getJdbcPassword() {
    return jdbcPassword;
  }

  /** {@inheritDoc} */
  @Override
  public String getJdbcUrl() {
    if(jdbcUrl.contains("jdbc:derby:memory:testdb;create=true")){
      // runs in unit test environmend
      return jdbcUrl.replace("testdb", System.getProperty("masterDbSchema"));
    }

    return jdbcUrl;
  }

  /** {@inheritDoc} */
  @Override
  public String getJdbcUser() {
    return jdbcUser;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isJndiEnabled() {
    return jndiEnabled;
  }

  /** {@inheritDoc} */
  @Override
  public String getJndiName() {
    return jndiName;
  }

  @Override
  public int getMaximumPoolSize() {
    return maximumPoolSize;
  }

  @Override
  public int getMinimumIdle() {
    return minimumIdle;
  }
}
