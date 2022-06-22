package ch.constructo.frontend.views.teachers;

import ch.constructo.frontend.views.MainLayout;
import ch.constructo.frontend.views.MainViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@PageTitle("Eap erstellen")
@Route(value = "createEap", layout = MainLayout.class)
@Secured({"ROLE_ADMIN"})
public class CreateEap extends MainViewFrame {

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    setViewContent(createContent());
  }

  private Component createContent(){
    VerticalLayout layout = new VerticalLayout();
    H2 title = new H2("Erstelle einen neuen Raport");

    layout.add(title);
    return layout;
  }
}
