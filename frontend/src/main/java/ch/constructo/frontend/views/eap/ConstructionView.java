package ch.constructo.frontend.views.eap;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.services.ConstructionStepService;
import ch.constructo.backend.services.GarmentService;
import ch.constructo.frontend.ui.components.FlexBoxLayout;
import ch.constructo.frontend.ui.components.navigation.AppBar;
import ch.constructo.frontend.ui.layout.Horizontal;
import ch.constructo.frontend.ui.layout.Top;
import ch.constructo.frontend.ui.util.BoxSizing;
import ch.constructo.frontend.views.MainLayout;
import ch.constructo.frontend.views.MainViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@PageTitle("Epa | Tool")
@Route(value = "epaTool", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ConstructionView extends MainViewFrame {

  @Autowired
  private GarmentService garmentService;

  @Autowired
  private ConstructionStepService constructionStepService;

  /* Input */
  private HorizontalLayout inputWrapper;
  private TextField stepType;
  private TextField stepText;
  private TextField stepUtility;
  private Button submit;

  /* Prepare */
  private VerticalLayout prepareWrapper;
  private Label prepareTitle;
  private Grid<ConstructionStep> prepareGrid;

  /* Construction */
  private VerticalLayout constructionWrapper;
  private Label constructionTitle;
  private Grid<ConstructionStep> constructionGrid;

  /* Finish */
  private VerticalLayout finishWrapper;
  private Label finishTitle;
  private Grid<ConstructionStep> finishGrid;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    setViewContent(createContent());
    //refreshGrid();
    //filter();
  }

  private Component createContent(){
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout imageLayout = new VerticalLayout();

    verticalLayout.add(createInputForm());
    verticalLayout.add(createPrepareList());
    verticalLayout.add(createConstructionList());
    verticalLayout.add(createFinishList());

    imageLayout.add(new Label("I am the image part"));

    FlexBoxLayout content = new FlexBoxLayout(verticalLayout, imageLayout);

    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setFlexDirection(FlexLayout.FlexDirection.ROW);
    content.setHeightFull();
    content.setWidthFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);

    return content;
  }

  private Component createInputForm(){
    inputWrapper = new HorizontalLayout();
    stepType = new TextField("Arbeitschritt");
    stepText = new TextField("Arbeitsmittel");
    stepUtility = new TextField("Betriebsmittel");
    submit = new Button("Bestätigen");

    inputWrapper.add(stepType, stepText, stepUtility, submit);
    return inputWrapper;
  }

  private Component createPrepareList(){
    prepareWrapper = new VerticalLayout();
    prepareTitle = new Label("Vorbereitung");

    prepareGrid = new Grid<>(ConstructionStep.class, false);
    prepareGrid.addClassName("construction-grid");
    prepareGrid.getElement().getStyle().set("heigt", "auto");

    prepareGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    prepareGrid.addColumn(new ComponentRenderer<>(this::createText))
        .setHeader("Arbeitsmittel")
        .setResizable(true);

    prepareGrid.addColumn(new ComponentRenderer<>(this::createUtitity))
        .setHeader("Betreibsmittel")
        .setResizable(true);

    prepareWrapper.add(prepareTitle, prepareGrid);
    return prepareWrapper;
  }

  private Component createConstructionList(){
    constructionWrapper = new VerticalLayout();
    constructionTitle = new Label("Montage");

    constructionGrid = new Grid<>(ConstructionStep.class, false);
    constructionGrid.addClassName("construction-grid");
    constructionGrid.getElement().getStyle().set("heigt", "auto");

    constructionGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    constructionGrid.addColumn(new ComponentRenderer<>(this::createText))
        .setHeader("Arbeitsmittel")
        .setResizable(true);

    constructionGrid.addColumn(new ComponentRenderer<>(this::createUtitity))
        .setHeader("Betreibsmittel")
        .setResizable(true);

    constructionWrapper.add(constructionTitle, constructionGrid);
    return constructionWrapper;
  }

  private Component createFinishList(){
    finishWrapper = new VerticalLayout();
    finishTitle = new Label("Finish");

    finishGrid = new Grid<>(ConstructionStep.class, false);
    finishGrid.addClassName("construction-grid");
    finishGrid.getElement().getStyle().set("heigt", "auto");

    finishGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    finishGrid.addColumn(new ComponentRenderer<>(this::createText))
        .setHeader("Arbeitsmittel")
        .setResizable(true);

    finishGrid.addColumn(new ComponentRenderer<>(this::createUtitity))
        .setHeader("Betreibsmittel")
        .setResizable(true);

    finishWrapper.add(finishTitle, finishGrid);
    return finishWrapper;
  }

  private Component createText(ConstructionStep constructionStep){
    return new Label(constructionStep.getStepType() != null ? constructionStep.getStepType().name() : "");
  }

  private Component createUtitity(ConstructionStep constructionStep){
    return new Label(constructionStep.getUitities() != null ? constructionStep.getUitities() : "");
  }


  private void initAppBar(){
    AppBar appBar = MainLayout.get().getAppBar();
    setViewContent(createContent());

    if (getContentPane() != null) {
      getContentPane().getStyle().set("width", "100%");
    }
  }
}
