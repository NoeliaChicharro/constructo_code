package ch.constructo.frontend.views.eap;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.entities.UserResult;
import ch.constructo.backend.services.ConstructionStepService;
import ch.constructo.backend.services.GarmentService;
import ch.constructo.backend.services.UserResultService;
import ch.constructo.backend.services.UserService;
import ch.constructo.frontend.security.SecurityUtils;
import ch.constructo.frontend.ui.components.FlexBoxLayout;
import ch.constructo.frontend.ui.components.navigation.AppBar;
import ch.constructo.frontend.ui.layout.Horizontal;
import ch.constructo.frontend.ui.layout.Top;
import ch.constructo.frontend.ui.util.BoxSizing;
import ch.constructo.frontend.views.MainLayout;
import ch.constructo.frontend.views.MainViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
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
  private TextField stepText;
  private TextField stepUtility;

  private Grid grid;
  private VerticalLayout gridWrapper;
  VerticalLayout imageLayout;
  private ListDataProvider<ConstructionStep> listDataProvider;
  private List<ConstructionStep> constructionSteps = new ArrayList<>();
  private List<ConstructionStep> correctAnswers = new ArrayList<>();
  private List<ConstructionStep> actualSteps;

  private UserResult userResult;
  private User user;

  private static Div hint;


  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    setViewContent(createContent());
    refreshGrid();
    findGarmentsConstructionStep();
    findUser();
    findUserResult();
  }

  private Component createContent(){
    VerticalLayout verticalLayout = new VerticalLayout();
    imageLayout = new VerticalLayout();

    verticalLayout.add(setupForm());
    verticalLayout.add(setupGrid());

    imageLayout.add(new Label(""));

    FlexBoxLayout content = new FlexBoxLayout(verticalLayout, imageLayout);

    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setFlexDirection(FlexLayout.FlexDirection.ROW);
    content.setHeightFull();
    content.setWidthFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);

    return content;
  }

  private Component setupForm() {
    stepText = new TextField("Arbeitsmittel");
    stepUtility = new TextField("Betriebsmittel");

    Button button = new Button("Send invite");
    button.getElement().getStyle().set("margin", "40px");
    button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    button.addClickShortcut(Key.ENTER);
    button.addClickListener(e -> {

      if (stepUtility.getValue() == null){
        stepUtility.setValue("");
      }
      ConstructionStep constructionStep = new ConstructionStep();
      constructionStep.setText(stepText.getValue());
      constructionStep.setGarment(findGarmentForNow());
      constructionStep.setUtilities(stepUtility.getValue());

      sendStep(constructionStep);
      stepText.setValue("");
      stepUtility.setValue("");
      stepText.focus();
    });

    return new HorizontalLayout(stepText, stepUtility, button);
  }

  private void sendStep(ConstructionStep constructionStep) {
    if (constructionStep == null || constructionSteps.contains(constructionStep))
      return;
    for (ConstructionStep actualStep : actualSteps) {
      if (constructionStep.getText().equals(actualStep.getText())) {
        setupImage(true);
        constructionSteps.add(constructionStep);
        int amount = userResult.getRightAmount();
        amount++;
        userResult.setRightAmount(amount);
        resultService.save(userResult);
        Notification.show(userResult.getRightAmount().toString());
      }
    }
    this.refreshGrid();
  }

  private Component setupImage(boolean isCorrect){
    Image image = new Image();
    if (isCorrect){
      image.setSrc("images/sleeve.png");
      image.setAlt("");
      imageLayout.add(image);
      return image;
    }
    return null;
  }

  private Component setupGrid() {
    gridWrapper = new VerticalLayout();
    gridWrapper.setPadding(false);
    grid = new Grid<>(ConstructionStep.class, false);
    grid.setAllRowsVisible(true);
    grid.addColumn(new ComponentRenderer<>(this::createText))
        .setHeader("Arbeitsmittel")
        .setResizable(true);
    grid.addColumn(new ComponentRenderer<>(this::createUtitity))
        .setHeader("Arbeitsmittel")
        .setResizable(true);
    grid.setItems(constructionSteps);

    hint = new Div();
    hint.setText("Es wurden noch keine Schritte eingefügt");
    hint.getStyle().set("padding", "var(--lumo-size-l)")
        .set("text-align", "center").set("font-style", "italic")
        .set("color", "var(--lumo-contrast-70pct)");

    gridWrapper.add(hint, grid);
    return gridWrapper;
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

  private void refreshGrid() {
    if (constructionSteps.size() > 0) {
      grid.setVisible(true);
      hint.setVisible(false);
      grid.getDataProvider().refreshAll();
    } else {
      grid.setVisible(false);
      hint.setVisible(true);
    }
  }

  private List<ConstructionStep> findGarmentsConstructionStep(){
    actualSteps = new ArrayList<>();
    actualSteps = constructionStepService.findByGarment(findGarmentForNow());
    return actualSteps;
  }

  private Garment findGarmentForNow(){
    return garmentService.findOne(1L);
  }

  private User findUser(){
    user = userService.findByUsername(SecurityUtils.getCurrentLoggedUserId());
    return user;
  }

  private UserResult findUserResult(){
    // @todo: check for garment two
    userResult = resultService.findByUser(user);
    if (userResult == null){
      UserResult newUserResult = new UserResult();
      newUserResult.setUser(findUser());
      newUserResult.setPassed(false);
      newUserResult.setGarment(findGarmentForNow());
      newUserResult.setRightAmount(0);
      userResult = resultService.save(newUserResult);
      return userResult;
    }

    return userResult;
  }
}
