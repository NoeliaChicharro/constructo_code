package ch.constructo.frontend.security;

import ch.constructo.frontend.ui.exceptions.AccessDeniedException;
import ch.constructo.frontend.views.login.LoginView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
        // nothing for now
      } else {
       // nothing for now -> registration view
      }
    }
  }
}
