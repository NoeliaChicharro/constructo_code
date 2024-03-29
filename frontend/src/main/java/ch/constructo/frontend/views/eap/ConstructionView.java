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
import ch.constructo.frontend.views.MainLayout.MenuItemInfo;
import ch.constructo.frontend.views.MainViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Epa | Tool")
@Route(value = "epaTool", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ConstructionView extends MainViewFrame implements HasUrlParameter<Long> {

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
  private Button send;
  private Button save;

  private Grid grid;
  VerticalLayout imageLayout;
  private final List<ConstructionStep> constructionSteps = new ArrayList<>();
  private final List<ConstructionStep> correctAnswers = new ArrayList<>();
  private List<ConstructionStep> actualSteps;

  private UserResult userResult;
  private User user;
  private Garment garment;

  private static Div hint;


  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    findGarmentsConstructionStep();
    findUser();
    findUserResult();
    setViewContent(createContent());
    refreshGrid();
    buttonsNotEnabled();
  }

  private Component createContent(){
    VerticalLayout verticalLayout = new VerticalLayout();
    imageLayout = new VerticalLayout();

    verticalLayout.add(setupForm());
    verticalLayout.add(setupGrid());
    verticalLayout.add(setupButtons());

    FlexBoxLayout content = new FlexBoxLayout(verticalLayout, imageLayout);

    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setFlexDirection(FlexLayout.FlexDirection.ROW);
    content.setHeightFull();
    content.setWidthFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);

    return content;
  }

  private Component setupForm() {
    stepText = new TextField("Arbeitsschritt");
    stepUtility = new TextField("Betriebsmittel");

    send = new Button(new MenuItemInfo.LineAwesomeIcon("la la-arrow-right"));
    send.getElement().getStyle().set("padding", "5px 5px 5px 15px");
    send.getElement().getStyle().set("margin", "40px");
    send.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    send.addClickShortcut(Key.ENTER);
    send.addClickListener(e -> {

      if (stepUtility.getValue() == null){
        stepUtility.setValue("");
      }
      ConstructionStep constructionStep = new ConstructionStep();
      constructionStep.setText(stepText.getValue());
      constructionStep.setGarment(garment);
      constructionStep.setUtilities(stepUtility.getValue());

      sendStep(constructionStep);
      stepText.setValue("");
      stepUtility.setValue("");
      stepText.focus();
    });

    return new HorizontalLayout(stepText, stepUtility, send);
  }

  private void sendStep(ConstructionStep constructionStep) {
    if (constructionStep == null || constructionSteps.contains(constructionStep))
      return;
    for (ConstructionStep actualStep : actualSteps) {
      if (constructionStep.getText().equals(actualStep.getText())) {
        if (!constructionSteps.isEmpty()){
          ConstructionStep step = constructionSteps.get(constructionSteps.size()-1);
          int order = step.getSortorder();
          int difference = actualStep.getSortorder() - order;
          if (difference == 1){
           saveStep(actualStep, constructionStep);
          } else {
            Notification.show("Reihenfolge stimmt nicht!", 2000, Notification.Position.MIDDLE);
          }
        }
        if (constructionSteps.isEmpty()){
          if (actualStep.getSortorder() == 1){
           saveStep(actualStep, constructionStep);
          }
        }
      }
    }
    this.refreshGrid();
  }

  private void saveStep(ConstructionStep actualStep, ConstructionStep constructionStep){
    setupImage(true, constructionStep.getText());
    constructionStep.setSortorder(actualStep.getSortorder());
    constructionSteps.add(constructionStep);
    int amount = userResult.getRightAmount();
    amount++;
    userResult.setRightAmount(amount);
    resultService.save(userResult);
  }

  private void setupImage(boolean isCorrect, String text){
    Image image = new Image();
    if (isCorrect){
      imageLayout.removeAll();
      image.setSrc("images/blouse/" + text  + ".png");
      image.setAlt("");
      imageLayout.add(image);
    }
  }

  private Component setupGrid() {
    VerticalLayout gridWrapper = new VerticalLayout();
    gridWrapper.setPadding(false);
    grid = new Grid<>(ConstructionStep.class, false);
    grid.setAllRowsVisible(true);
    grid.addColumn(new ComponentRenderer<>(this::createText))
        .setHeader("Arbeitsschritt")
        .setResizable(true);
    grid.addColumn(new ComponentRenderer<>(this::createUtitity))
        .setHeader("Betriebsmittel")
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

  private Component setupButtons(){
    HorizontalLayout buttonLayout = new HorizontalLayout();
    buttonLayout.setPadding(false);

    Button cancel = new Button("Abbrechen");
    cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    cancel.addClickListener(e -> {
      UI.getCurrent().navigate(EapView.class);
    });

    save = new Button("Speichern");
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickListener(e -> {
      if (userResult != null){
        int actualAmount = actualSteps.size();
        int userAmount = constructionSteps.size();
        int allowedDifference = 10;
        if ((userAmount / actualAmount * 100) + allowedDifference >= 100){
          userResult.setPassed(true);
          Notification.show("Du hast bestanden", 2000, Notification.Position.MIDDLE);
        } else {
          userResult.setPassed(false);
          Notification.show("Leider nicht bestanden", 2000, Notification.Position.MIDDLE);
        }
        resultService.save(userResult);
      }
    });
    buttonLayout.add(cancel, save);
    return buttonLayout;
  }

  private void buttonsNotEnabled(){
    if (userResult.getPassed()){
      send.setEnabled(false);
      save.setEnabled(false);
    }
  }

  private void refreshGrid() {
    if (userResult.getPassed()){
      constructionSteps.addAll(actualSteps);
      Image image = new Image();
      image.setSrc("images/blouse/Endkontrolle.png");
      imageLayout.add(image);
      stepText.setEnabled(false);
      stepUtility.setEnabled(false);
    } else {
      userResult.setRightAmount(0);
    }

    if (constructionSteps.size() > 0) {
      grid.setVisible(true);
      hint.setVisible(false);
      grid.getDataProvider().refreshAll();
    } else {
      grid.setVisible(false);
      hint.setVisible(true);
    }
  }

  private void findGarmentsConstructionStep(){
    actualSteps = new ArrayList<>();
    actualSteps = constructionStepService.findByGarment(garment);
  }

  private User findUser(){
    user = userService.findByUsername(SecurityUtils.getCurrentLoggedUserId());
    return user;
  }

  private void findUserResult(){
    userResult = resultService.findByUserAndGarment(user, garment);
    if (userResult == null){
      UserResult newUserResult = new UserResult();
      newUserResult.setUser(findUser());
      newUserResult.setPassed(false);
      newUserResult.setGarment(garment);
      newUserResult.setRightAmount(0);
      userResult = resultService.save(newUserResult);
    }
  }

  @Override
  public void setParameter(BeforeEvent beforeEvent, Long id) {
    garment = garmentService.findOne(id);
  }
}
