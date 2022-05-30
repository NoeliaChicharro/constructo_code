package ch.constructo.frontend.views.eap;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.services.GarmentService;
import ch.constructo.frontend.ui.components.FlexBoxLayout;
import ch.constructo.frontend.ui.components.navigation.AppBar;
import ch.constructo.frontend.ui.layout.Horizontal;
import ch.constructo.frontend.ui.layout.Top;
import ch.constructo.frontend.ui.util.BoxSizing;
import ch.constructo.frontend.ui.util.UIUtils;
import ch.constructo.frontend.views.MainLayout;
import ch.constructo.frontend.views.MainViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import com.vaadin.flow.component.Component;

//@Component
@PageTitle("Epa")
@Route(value = "epa", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class EapView extends MainViewFrame {

  @Autowired
  private GarmentService garmentService;

  private Grid<Garment> grid;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    setViewContent(createContent());
    //filter();
  }

  private Component createContent(){
    VerticalLayout verticalLayout = new VerticalLayout();

    //verticalLayout.add(createSearchField());
    verticalLayout.add(createGrid());

    FlexBoxLayout content = new FlexBoxLayout(verticalLayout);

    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeightFull();
    content.setWidthFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);

    return content;
  }

  private Component createGrid(){
    grid = new Grid<>(Garment.class, false);
    grid.addClassName("garment-grid");
    grid.setSizeFull();

    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.addColumn(new ComponentRenderer<>(this::createName))
        .setHeader("Name")
        .setResizable(true);

    grid.addColumn(new ComponentRenderer<>(this::createDescription))
        .setHeader("Beschreibung")
        .setResizable(true);

    grid.addColumn(new ComponentRenderer<>(this::createGarmentType))
        .setHeader("Type")
        .setResizable(true);

    return grid;
  }

  private Component createName(Garment garment){
    return new Label(garment.getName() != null ? garment.getName() : "");
  }

  private Component createDescription(Garment garment){
    return new Label(garment.getDescription() != null ? garment.getDescription() : "");
  }

  private Component createGarmentType(Garment garment){
    return new Label(garment.getGarmentCategory() != null ? garment.getGarmentCategory().name() : "");
  }

  private void initAppBar(){
    AppBar appBar = MainLayout.get().getAppBar();
    setViewContent(createContent());

    if (getContentPane() != null) {
      getContentPane().getStyle().set("width", "100%");
    }
  }

}
