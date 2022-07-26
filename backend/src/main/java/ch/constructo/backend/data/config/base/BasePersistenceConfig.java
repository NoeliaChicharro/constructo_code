package ch.constructo.backend.data.config.base;

import ch.constructo.backend.data.config.Constants;
import ch.constructo.backend.data.config.base.JpaProperties.Key;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;

public abstract class BasePersistenceConfig {

  protected final Logger log = LoggerFactory.getLogger(this.getClass());


  private static final Map<String, Boolean> alreadyInitialized = Collections.synchronizedMap(new HashMap<>());

  public PlatformTransactionManager transactionManager() {
    EntityManagerFactory factory = entityManagerFactory().getObject();
    return new JpaTransactionManager(factory);
  }

  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(Boolean.FALSE);
    vendorAdapter.setShowSql(Boolean.valueOf(getHibernateShowSql()));

    factory.setDataSource(dataSource());
    factory.setJpaVendorAdapter(vendorAdapter);

    factory.setPackagesToScan(scanDomainPackages());

    factory.setJpaProperties(additionalJpaProperties());

    String persistenceUnitName = Constants.MASTER_PERSISTENCE_UNIT_NAME;

    log.info("create persistence unit: " + persistenceUnitName);
    factory.setPersistenceUnitName(persistenceUnitName);
    factory.afterPropertiesSet();
    factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
    return factory;
  }

  public abstract String[] scanDomainPackages();

  @Bean
  public HibernateExceptionTranslator hibernateExceptionTranslator() {
    return new HibernateExceptionTranslator();
  }

  public DataSource dataSource() {

    if (isJndiEnabled()) {
      final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
      dsLookup.setResourceRef(true);
      return dsLookup.getDataSource(getJndiName());
    } else {

      HikariConfig config = new HikariConfig();

      config.setDriverClassName(getJdbcDriver());
      config.setJdbcUrl(getJdbcUrl());
      config.setUsername(getJdbcUser());
      config.setPassword(getJdbcPassword());

      config.setMaximumPoolSize(getMaximumPoolSize());
      config.setMinimumIdle(getMinimumIdle());

      /* nur mit Cache Config
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
      */
      if (getHibernateDialect().contains("SQLServer2012Dialect")) {
        config.setConnectionTestQuery("Select 1;");
      }

      return new HikariDataSource(config);

    }
  }

  protected abstract int getMinimumIdle();

  protected abstract int getMaximumPoolSize();

  public static Map<String, Boolean> getAlreadyInitialized() {
    return alreadyInitialized;
  }

  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    if (dataSource instanceof AbstractRoutingDataSource) {
      DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
      dataSourceInitializer.setDataSource(dataSource);
      return dataSourceInitializer;
    } else {
      DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
      dataSourceInitializer.setDataSource(dataSource);
      if (!alreadyInitialized.containsKey(getJdbcUrl()) && isInitDatabase()) {
        try {
          ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
          databasePopulator.addScript(getInitScript());
          dataSourceInitializer.setDatabasePopulator(databasePopulator);
          dataSourceInitializer.setEnabled(isInitDatabase());

          setInitDatabase(false);
          alreadyInitialized.put(getJdbcUrl(), Boolean.TRUE);
        } catch (Exception e) {
          log.warn("Database already configured or configuration failed:", e);
        }
      }
      return dataSourceInitializer;
    }

  }

  protected abstract void setInitDatabase(boolean initDatabase);

  protected Properties additionalJpaProperties() {
    Properties properties = new Properties();
    properties.setProperty(Key.HIBERNATE_HBM2DDL_AUTO, getHibernateHBM2DDLauto());
    //properties.setProperty(JpaProperties.Key.HIBERNATE_SHOW_SQL, env.getProperty(JpaProperties.Key.HIBERNATE_SHOW_SQL));
    properties.setProperty(Key.HIBERNATE_DIALECT, getHibernateDialect());


    /* nur mit Cache Config
    properties.setProperty("hibernate.cache.use_second_level_cache", "true");
    properties.setProperty("hibernate.cache.use_query_cache", "true");
    properties.setProperty("hibernate.cache.use_reference_entries", "true");

    properties.setProperty("org.hibernate.cache.ehcache.configurationResourceName", "ehcache.xml");
    properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
    properties.setProperty("hibernate.generate_statistics", "true");

     */

    properties.setProperty("hibernate.max_fetch_depth", "8");

    //for Batch Updates
    properties.setProperty("hibernate.jdbc.batch_size", "500");
    properties.setProperty("hibernate.order_inserts", "true");
    properties.setProperty("hibernate.order_updates", "true");
    properties.setProperty("hibernate.batch_versioned_data", "true");


    properties.setProperty("autoRegisterUserTypes", "true");

    if(isJndiEnabled()){
      log.info(format("additional properties used for %s", getJndiName()));
    } else {
      log.info(format("additional properties used for %s", getJdbcUrl()));
    }

    for (String name: properties.stringPropertyNames()){
      log.info(format("%s : %s", name, properties.getProperty(name)));
    }

    return properties;
  }

  public abstract String getHibernateDialect();

  public abstract String getHibernateHBM2DDLauto();

  public abstract String getHibernateShowSql();

  public abstract boolean isInitDatabase();

  public abstract Resource getInitScript() ;

  public abstract String getJdbcDriver() ;

  public abstract String getJdbcPassword();

  public abstract String getJdbcUrl();

  public abstract String getJdbcUser();

  public abstract boolean isJndiEnabled();

  public abstract String getJndiName();
}

