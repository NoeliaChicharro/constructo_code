package ch.constructo.backend.data.config.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ConfigurationException;

import static java.lang.String.format;

public class ConstructoRoutingDataSource extends AbstractConstructoRoutingDataSource {
  private final transient Logger log = LoggerFactory.getLogger(getClass());

  /**
   * <p>Constructor for SidacRoutingDataSource.</p>
   *
   * @param dataSourceMap a {@link DataSourceMapDescriptor} object.
   */
  public ConstructoRoutingDataSource(DataSourceMapDescriptor dataSourceMap) {
    super(dataSourceMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object determineCurrentLookupKey() {
    String context = DbContextHolder.getDbType();

    if (context != null) {
      return context;
    } else {
      try {
        throw new ConfigurationException("no DB Context found!");
      } catch (ConfigurationException e) {
        e.printStackTrace();
      }
      return null;
    }
  }
}




