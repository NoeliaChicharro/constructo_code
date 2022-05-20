package ch.constructo.master.data.config;

import ch.constructo.master.data.core.data.config.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {
    Constants.CH_QUALICASA_MASTER_DATA,
    Constants.CH_QUALICASA_MASTER_SERVICES
})
@PropertySource(value = "classpath:masterdata.properties")
@EnableAspectJAutoProxy
@EnableCaching
public class DbConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /**
   * <p>cacheManager.</p>
   *
   * @return a {@link org.springframework.cache.CacheManager} object.
   */
  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }
}
