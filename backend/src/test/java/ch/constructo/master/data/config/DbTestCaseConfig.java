package ch.constructo.master.data.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ch.constructo.master.data.test.ConstructoPersistenceConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles()
@ContextConfiguration(classes = {DbConfig.class, ConstructoPersistenceConfig.class})
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class
})
public @interface DbTestCaseConfig {
}
