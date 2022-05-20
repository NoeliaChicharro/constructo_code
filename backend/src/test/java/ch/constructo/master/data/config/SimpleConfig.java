package ch.constructo.master.data.config;

import ch.constructo.master.data.core.data.config.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = {Constants.CH_QUALICASA_MASTER_DATA})
@EnableTransactionManagement
public class SimpleConfig {

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder.setType(EmbeddedDatabaseType.DERBY).build();
  }

  /**
   * <p>entityManagerFactory.</p>
   *
   * @return a {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean} object.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.DERBY);
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory =
        new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan(Constants.CH_QUALICASA_MASTER_DOMAIN_PACKAGE);
    factory.setDataSource(dataSource());

    return factory;
  }

  /**
   * <p>transactionManager.</p>
   *
   * @param entityManagerFactory a {@link javax.persistence.EntityManagerFactory} object.
   * @return a {@link org.springframework.transaction.PlatformTransactionManager} object.
   * @throws java.sql.SQLException if any.
   */
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws SQLException {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }
}
