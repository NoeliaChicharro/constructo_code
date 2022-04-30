package ch.constructo.backend.security;

import ch.constructo.frontend.ui.exceptions.AccessDeniedException;
import ch.constructo.frontend.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

  @Override
  public void serviceInit(ServiceInitEvent event){
    event.getSource().addUIInitListener(uiInitEvent -> {
      final UI ui = uiInitEvent.getUI();
      ui.addBeforeEnterListener(this::authenticateNavigation);
    });
  }

  private void authenticateNavigation(BeforeEnterEvent event){
    final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
    if (!accessGranted) {
      if (SecurityUtils.isUserLoggedIn()) {
        event.rerouteToError(AccessDeniedException.class);
      } else {
        /*if ( ( !LoginView.class.equals(event.getNavigationTarget()) &&
            !RegistrationView.class.equals(event.getNavigationTarget()) )
            && !SecurityUtils.isUserLoggedIn()) {

          boolean needsReroute = !RegistrationView.class.equals(event.getNavigationTarget());
          if(needsReroute){
            event.rerouteTo(LoginView.class);
          }
        }*/
      }
    }
  }
}
