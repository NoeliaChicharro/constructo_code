package ch.constructo.backend.data.config.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class AbstractConstructoRoutingDataSource extends AbstractRoutingDataSource {

  private static final Logger log = LoggerFactory.getLogger(AbstractConstructoRoutingDataSource.class);

  private DataSourceMapDescriptor dataSourceMap;

  /**
   * <p>Constructor for AbstractConstructoRoutingDataSource.</p>
   *
   * @param dataSourceMap a {@link ch.constructo.backend.config.DataSourceMapDescriptor} object.
   */
  public AbstractConstructoRoutingDataSource(DataSourceMapDescriptor dataSourceMap) {
    this.dataSourceMap = dataSourceMap;
  }

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
