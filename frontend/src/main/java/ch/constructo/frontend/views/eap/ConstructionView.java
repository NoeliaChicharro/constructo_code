package ch.constructo.frontend.views.eap;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.UserResult;
import ch.constructo.backend.data.enums.StepType;
import ch.constructo.backend.services.ConstructionStepService;
import ch.constructo.backend.services.GarmentService;
import ch.constructo.backend.services.UserResultService;
import ch.constructo.backend.services.UserService;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle("Epa | Tool")
@Route(value = "epaTool", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ConstructionView extends MainViewFrame {

  @Autowired
  private GarmentService garmentService;

  @Autowired
  private ConstructionStepService constructionStepService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserResultService resultService;

  /* Input */
  private HorizontalLayout inputWrapper;
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

  private ListDataProvider<ConstructionStep> listDataProvider;
  private List<ConstructionStep> constructionSteps = new ArrayList<>();
  private List<ConstructionStep> correctAnswers = new ArrayList<>();

  private Label step = new Label();
  private List<Label> labels = new ArrayList<>();
  VerticalLayout labelLayout = new VerticalLayout();

  // Make list (findAll) at the beginning, use this list on submit to check if user input is correct.
  // Insert Values into grid (refresh grid)

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    setViewContent(createContent());
    refreshGrid();
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
    stepText = new TextField("Arbeitsmittel");
    stepUtility = new TextField("Betriebsmittel");
    submit = new Button("Bestätigen");

    submit.addClickListener(e -> {
      for (ConstructionStep constructionStep : constructionSteps) {
        if (constructionStep.getText().equals(stepText.getValue())) {
          ConstructionStep constructionStepNew = new ConstructionStep();
          constructionStepNew.setStepType(constructionStep.getStepType());
          constructionStepNew.setGarment(constructionStep.getGarment());
          constructionStepNew.setText(stepText.getValue());
          constructionStepNew.setUtilities(stepUtility.getValue());
          correctAnswers.add(constructionStepNew);
          Notification.show("Whue, correct!");
          // Refresh here;
          break;
        }
      }
    });

    inputWrapper.add(stepText, stepUtility, submit);
    return inputWrapper;
  }

  private Component createPrepareList(){
    prepareWrapper = new VerticalLayout();
    prepareWrapper.setHeight("350px");
    prepareTitle = new Label("Vorbereitung");

    prepareGrid = new Grid<>(ConstructionStep.class, false);
    prepareGrid.addClassName("construction-grid");
    prepareGrid.getElement().getStyle().set("heigt", "200px");

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
    constructionWrapper.setHeight("350px");
    constructionTitle = new Label("Montage");

    constructionGrid = new Grid<>(ConstructionStep.class, false);
    constructionGrid.addClassName("construction-grid");
    constructionGrid.getElement().getStyle().set("heigt", "200px");

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
    finishWrapper.setHeight("350px");
    finishTitle = new Label("Finish");

    finishGrid = new Grid<>(ConstructionStep.class, false);
    finishGrid.addClassName("construction-grid");
    finishGrid.getElement().getStyle().set("heigt", "200px");

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
    return new Label(constructionStep.getText() != null ? constructionStep.getText() : "");
  }

  private Component createUtitity(ConstructionStep constructionStep){
    return new Label(constructionStep.getUtilities() != null ? constructionStep.getUtilities() : "");
  }

  private void initAppBar(){
    AppBar appBar = MainLayout.get().getAppBar();
    setViewContent(createContent());

    if (getContentPane() != null) {
      getContentPane().getStyle().set("width", "100%");
    }
  }

  /* Prepare */
  void findAllPrepareSteps(){
    Collection<ConstructionStep> content = new ArrayList<>();
    content = executeFindAllPrepare();
    fillPrepareGrid(content);
  }

  private void fillPrepareGrid(Collection<ConstructionStep> content) {
    gridHandling(prepareGrid, content);
  }

  /* Constuction */
  void findAllConstructionSteps(){
    Collection<ConstructionStep> content = new ArrayList<>();
    content = executeFindAllConstruction();
    fillConstructionGrid(content);
  }

  private void fillConstructionGrid(Collection<ConstructionStep> content) {
    gridHandling(constructionGrid, content);
  }

  /* Finish */
  void findAllFinishSteps(){
    Collection<ConstructionStep> content = new ArrayList<>();
    content = executeFindAllFinish();
    fillFinishGrid(content);
  }

  private void fillFinishGrid(Collection<ConstructionStep> content) {
    gridHandling(finishGrid, content);
  }

  private void gridHandling(Grid<ConstructionStep> grid, Collection<ConstructionStep> content){
    if(content.size()==0){
      listDataProvider = DataProvider.ofCollection(new ArrayList<>());
      grid.setDataProvider(listDataProvider);
    } else {
      listDataProvider = DataProvider.ofCollection(content);
      grid.setDataProvider(listDataProvider);
    }
  }

  private void refreshGrid() {
    findAllPrepareSteps();
    findAllConstructionSteps();
    findAllFinishSteps();
  }

  private List<ConstructionStep> executeFindAllPrepare(){
    List<ConstructionStep> prep = constructionStepService.findAllByStepType(StepType.PREPARE);
    constructionSteps.addAll(prep);
    return prep;
  }

  private List<ConstructionStep> executeFindAllConstruction(){
    List<ConstructionStep> cons = constructionStepService.findAllByStepType(StepType.CONSTRUCTION);
    constructionSteps.addAll(cons);
    return cons;
  }

  private List<ConstructionStep> executeFindAllFinish(){
    List<ConstructionStep> finish = constructionStepService.findAllByStepType(StepType.FINISH);
    constructionSteps.addAll(finish);
    return finish;
  }
}
