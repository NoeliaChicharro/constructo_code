package ch.constructo.backend.data.config;

import java.util.Map;

public interface DataSourceMapDescriptor {

  Map<Object, Object> getDataSourceMap();

  void removeSource(String session, String databaseName);
}
