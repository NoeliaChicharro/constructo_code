package ch.constructo.master.data.core.data.config;

public class DbContextHolder {
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

  public static void setDbType(String dbType) {
    if(dbType == null){
      throw new NullPointerException();
    }
    contextHolder.set(dbType);
  }

  public static String getDbType() {
    return (String) contextHolder.get();
  }

  public static void clearDbType() {
    contextHolder.remove();
  }

}
