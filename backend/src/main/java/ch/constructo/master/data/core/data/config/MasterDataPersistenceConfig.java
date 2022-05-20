package ch.constructo.master.data.core.data.config;

import ch.constructo.master.data.core.data.profiler.RepositoryProfiler;
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
@EnableTransactionManagement(order = 100)
@EnableJpaRepositories(
    basePackages = {Constants.CH_QUALICASA_MASTER_REPOSITORY_PACKAGE},
    entityManagerFactoryRef = Constants.CH_QUALICASA_MASTER_EM,
    transactionManagerRef = Constants.CH_QUALICASA_MASTER_DATA_TRX
)
@ComponentScan(basePackages = {
    Constants.CH_QUALICASA_MASTER_SERVICES})

@EnableAspectJAutoProxy
public class MasterDataPersistenceConfig extends BasePersistenceConfig{

  @Value("${master.use-jndi:false}")
  private boolean jndiEnabled;

  @Value("${master.jndiName:java:comp/env/jdbc/<dbName>}")
  private String jndiName;

  @Value("${master.jdbc.driverClassName:com.microsoft.sqlserver.jdbc.SQLServerDriver}")
  private String jdbcDriver;

  @Value("${master.jdbc.url:jdbc:derby:memory:testdb;create=true}")
  private String jdbcUrl;

  @Value("${master.jdbc.username:APP}")
  private String jdbcUser;

  @Value("${master.jdbc.password:}")
  private String jdbcPassword;

  @Value("${master.init-db:false}")
  private boolean initDatabase;

  @Value("${master.init-script:initial-masterdata.sql}")
  private Resource initScript;

  @Value("${master.hibernate.dialect:org.hibernate.dialect.SQLServer2012Dialect}")
  private String hibernateDialect;

  @Value("${master.hibernate.show_sql:false}")
  private String hibernateShowSql;

  @Value("${master.hibernate.hbm2ddl.auto:}")
  private String hibernateHBM2DDLauto;

  @Value("${master.maximumPoolSize:30}")
  private int maximumPoolSize;

  @Value("${master.minimumIdle:15}")
  private int minimumIdle;


  @Bean
  public RepositoryProfiler repositoryProfiler(){
    return new RepositoryProfiler();
  }

  /**
   * <p>scanDomainPackages.</p>
   *
   * @return an array of {@link String} objects.
   */
  public String[] scanDomainPackages() {
    return new String[]{Constants.CH_QUALICASA_MASTER_DOMAIN_PACKAGE};
  }

  /**
   * <p>transactionManager.</p>
   *
   * @return a {@link PlatformTransactionManager} object.
   */
  @Bean(name = Constants.CH_QUALICASA_MASTER_DATA_TRX)
  public PlatformTransactionManager masterDataTrxManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(masterdataEM().getObject());
    return transactionManager;
  }

  /**
   * <p>masterdataEM.</p>
   *
   * @return a {@link LocalContainerEntityManagerFactoryBean} object.
   */
  @Bean(name = Constants.CH_QUALICASA_MASTER_EM)
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
   * <p>masterdataDS.</p>
   *
   * @return a {@link DataSource} object.
   */
  @Bean(name = Constants.CH_QUALICASA_MASTER_DS)
  public DataSource masterdataDS() {
    return super.dataSource();
  }
  /**
   * <p>masterDataSourceInitializer.</p>
   *
   * @param dataSource a {@link DataSource} object.
   * @return a {@link DataSourceInitializer} object.
   */
  @Bean(name = "masterDataSourceInitializer")
  public DataSourceInitializer masterDataSourceInitializer(@Qualifier(Constants.CH_QUALICASA_MASTER_DS) DataSource dataSource) {
    DataSourceInitializer dataSourceInitializer = super.dataSourceInitializer(dataSource);
    initDatabase = false;  // overwrite it, that only one time will be executed
    return dataSourceInitializer;
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
    if(jdbcUrl.contains("jdbc:derby:memory:masterdata;create=true")){
      // runs in unit test environmend
      return jdbcUrl.replace("masterdata", System.getProperty("masterDbSchema"));
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
