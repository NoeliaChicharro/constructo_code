package ch.constructo.frontend.views.dashboard;

import ch.constructo.frontend.security.SecurityUtils;
import ch.constructo.frontend.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.security.access.annotation.Secured;

@PageTitle("Constructo")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class DashboardView extends VerticalLayout {

  private H2 welcome;
  private Image background;

  public DashboardView() {
    welcome = new H2("Willkommen zurück " + SecurityUtils.getCurrentLoggedUserId());
    background = new Image();
    background.setMaxWidth("100vw");
    background.setMaxHeight("70vh");
    background.setSrc("images/dashboard/ladies.png");

    setMargin(true);
    setAlignItems(Alignment.CENTER);

    add(welcome, background);
  }
}
