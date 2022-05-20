package ch.constructo.master.data.config;

import ch.constructo.master.data.repositories.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = UserRepository.class)
@ComponentScan(basePackages = {
      "ch.constructo.backend.services",})
public class BackendConfig {

}
