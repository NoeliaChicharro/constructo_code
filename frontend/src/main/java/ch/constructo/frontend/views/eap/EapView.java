package ch.constructo.frontend.views.eap;

import ch.constructo.frontend.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
@PageTitle("Epa")
@Route(value = "epa", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class EapView extends HorizontalLayout {

  private TextField input;
  private Button submit;

  public EapView(){
    setMargin(true);

    input = new TextField("Input");
    submit = new Button("Enter");

    add();
  }
}
