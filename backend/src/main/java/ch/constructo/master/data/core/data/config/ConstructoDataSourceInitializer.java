package ch.constructo.master.data.core.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstructoDataSourceInitializer extends DataSourceInitializer {

  protected final Logger log = LoggerFactory.getLogger(this.getClass());

  private AbstractConstructoRoutingDataSource dataSource;
  private DatabasePopulator databasePopulator;
  private DatabasePopulator databaseCleaner;
  private boolean enabled = true;

  private static Map<String, Boolean> alreadyInitialized = Collections.synchronizedMap(new HashMap<>());


  /**
   * <p>Constructor for SidacDataSourceInitializer.</p>
   *
   * @param dataSource a {@link AbstractConstructoRoutingDataSource} object.
   */
  public ConstructoDataSourceInitializer(AbstractConstructoRoutingDataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * {@inheritDoc}
   */
  public void setDataSource(DataSource dataSource) {
    throw new IllegalStateException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   */
  public void setDatabasePopulator(DatabasePopulator databasePopulator) {
    throw new IllegalStateException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   */
  public void setDatabaseCleaner(DatabasePopulator databaseCleaner) {
    throw new IllegalStateException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   */
  public void setEnabled(boolean enabled) {
    throw new IllegalStateException("Not implemented yet");
  }

  /**
   * <p>afterPropertiesSet.</p>
   */
  public void afterPropertiesSet() {

    this.execute();
  }

  /**
   * <p>destroy.</p>
   */
  public void destroy() {
    this.cleanup();
  }

  private void cleanup() {
    // @TODO implement cleanup
  }

  private void execute() {
    Assert.state(this.dataSource != null, "DataSource must be set");

    DataSourceMapDescriptor dataSourceMap = dataSource.getDataSourceMap();
    Map<Object, Object> sourceMap = dataSourceMap.getDataSourceMap();

    for (Map.Entry<Object, Object> entry : sourceMap.entrySet()) {
      if (entry.getValue() instanceof DataSourcAdapter) {
        DataSourcAdapter dataSourceAdapter = (DataSourcAdapter) entry.getValue();
        DatabaseProperties databaseProperties = dataSourceAdapter.getDatabaseProperties();
        if (!alreadyInitialized.containsKey(dataSourceAdapter.getDatabaseProperties().getJdbcUrl()) && databaseProperties.isInitDatabase()) {
          try {
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScript(dataSourceAdapter.getDatabaseProperties().getInitScript());
            DatabasePopulatorUtils.execute(databasePopulator, dataSourceAdapter);

            alreadyInitialized.put(dataSourceAdapter.getDatabaseProperties().getJdbcUrl(), Boolean.TRUE);
          } catch (Exception e) {
            log.warn("Database already configured or configuration failed:", e);
          }
        }
      }

    }


  }
}
