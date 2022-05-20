package ch.constructo.master.data.core.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class AbstractConstructoRoutingDataSource extends AbstractRoutingDataSource {

  private static Logger log = LoggerFactory.getLogger(AbstractConstructoRoutingDataSource.class);

  private DataSourceMapDescriptor dataSourceMap;

  /**
   * <p>Constructor for AbstractSidacRoutingDataSource.</p>
   *
   * @param dataSourceMap a {@link ch.constructo.backend.config.DataSourceMapDescriptor} object.
   */
  public AbstractConstructoRoutingDataSource(DataSourceMapDescriptor dataSourceMap) {
    this.dataSourceMap = dataSourceMap;
  }

  /**
   * <p>Getter for the field <code>dataSourceMap</code>.</p>
   *
   * @return a {@link ch.constructo.backend.config.DataSourceMapDescriptor} object.
   */
  public DataSourceMapDescriptor getDataSourceMap() {
    return dataSourceMap;
  }


  public synchronized void updateDateSources(DataSourceMapDescriptor dataSourceMap){
    this.dataSourceMap = dataSourceMap;
    setTargetDataSources(dataSourceMap.getDataSourceMap());

  }

  @Override
  protected Object determineCurrentLookupKey() {
    return null;
  }
}
