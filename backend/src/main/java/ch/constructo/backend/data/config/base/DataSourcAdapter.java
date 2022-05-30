package ch.constructo.backend.data.config.base;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DataSourcAdapter implements DataSource {
  private final DataSource dataSource;
  private DatabaseProperties databaseProperties;

  /**
   * <p>Constructor for DataSourceAdapter.</p>
   *
   * @param databaseProperties a {@link DatabaseProperties} object.
   * @param dataSource         a {@link javax.sql.DataSource} object.
   */
  public DataSourcAdapter(DataSource dataSource, DatabaseProperties databaseProperties) {
    this.dataSource = dataSource;
    this.databaseProperties = databaseProperties;
  }

  /**
   * <p>Getter for the field <code>databaseProperties</code>.</p>
   *
   * @return a {@link DatabaseProperties} object.
   */
  public DatabaseProperties getDatabaseProperties() {
    return databaseProperties;
  }

  /**
   * <p>Setter for the field <code>databaseProperties</code>.</p>
   *
   * @param databaseProperties a {@link DatabaseProperties} object.
   */
  public void setDatabaseProperties(DatabaseProperties databaseProperties) {
    this.databaseProperties = databaseProperties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return dataSource.getConnection(username, password);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getLoginTimeout() throws SQLException {
    return dataSource.getLoginTimeout();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return dataSource.getLogWriter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return dataSource.getParentLogger();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    dataSource.setLoginTimeout(seconds);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    dataSource.setLogWriter(out);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return dataSource.isWrapperFor(iface);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return dataSource.unwrap(iface);
  }
}
