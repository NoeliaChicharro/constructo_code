package ch.constructo.master.data.config;

import ch.constructo.backend.data.config.Constants;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {
    Constants.CH_QUALICASA_MASTER_DATA,
    Constants.CH_QUALICASA_MASTER_SERVICES
})
@PropertySource(value = "classpath:/masterdata.properties")
@EnableAspectJAutoProxy
public class DbConfig {


  @Bean
  public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }


}
