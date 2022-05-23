package ch.constructo.master.data.config;

import ch.constructo.backend.data.config.ConstructoPersistenceConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfig.class, ConstructoPersistenceConfig.class})

public class TestDbConfig {

    @Test
    public void testCOnfig(){

    }
}
