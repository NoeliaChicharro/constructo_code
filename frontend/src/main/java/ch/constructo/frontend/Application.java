package ch.constructo.frontend;

import ch.constructo.backend.config.BackendConfig;
import ch.constructo.backend.data.config.ConstructoPersistenceConfig;
import ch.constructo.frontend.security.SecurityConfiguration;
import ch.constructo.frontend.views.MainLayout;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackageClasses = {
    SecurityConfiguration.class,
    BackendConfig.class,
    MainLayout.class,
    Application.class,
})
@Import( {ConstructoPersistenceConfig.class})
@PropertySource(value = "classpath:/masterdata.properties")
@NpmPackage(value = "@fontsource/barlow", version = "4.5.0")
@Theme(value = "constructo", variant = Lumo.LIGHT)
//@PWA(name = "Constructo", shortName = "Constructo", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
