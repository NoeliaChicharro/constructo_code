package ch.constructo.frontend.views.dashboard;

import ch.constructo.frontend.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Constructo")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class DashboardView extends HorizontalLayout {

  private TextField name;
  private Button sayHello;

  public DashboardView() {
    name = new TextField("Your name");
    sayHello = new Button("Say hello");
    sayHello.addClickListener(e -> {
      Notification.show("Hello " + name.getValue());
    });

    setMargin(true);
    setVerticalComponentAlignment(Alignment.END, name, sayHello);

    add(name, sayHello);
  }
}
