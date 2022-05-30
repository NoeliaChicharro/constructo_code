package ch.constructo.backend.config;

import ch.constructo.backend.data.config.ConstructoPersistenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfig.class, ConstructoPersistenceConfig.class})
public class TestDbConfig {

  /**
   * <p>Test Db Config</p>
   */
  @Test
  public void testCOnfig(){
  }
}
