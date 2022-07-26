package ch.constructo.frontend.views.teachers;

import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.enums.Role;
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
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle("Studenten Liste")
@Route(value = "studentListView", layout = MainLayout.class)
@Secured({"ROLE_ADMIN"})
public class StudentListView extends MainViewFrame {

  @Autowired
  protected UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  //protected PatternForm form;
  private Grid<User> grid;
  private TextField searchField;
  private Dialog dialog;
  List<User> students;

  // Form Dialog
  FormLayout formLayout = new FormLayout();
  TextField firstName = new TextField("Vorname");
  TextField lastName = new TextField("Nachname");
  TextField username = new TextField("Benutzername");
  TextField email = new TextField("Email");
  TextField plainPw = new TextField("Passwort");
  ComboBox<Role> role = new ComboBox<>("Rolle");

  private String generatedUsername = "";
  private String firstLetter = "";
  private String secondLetters = "";

  private final Button save = new Button(VaadinIcon.SAFE.create());
  private final Button cancel = new Button("Abbrechen");

  private final Binder<User> binder = new BeanValidationBinder<>(User.class);

  private User currentUser;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    executeFindAllUsers();
    setViewContent(createContent());
  }

  private void initAppBar(){
    AppBar appBar = MainLayout.get().getAppBar();
    setViewContent(createContent());

    if (getContentPane() != null) {
      getContentPane().getStyle().set("width", "100%");
    }
  }

  private Component createContent(){
    VerticalLayout verticalLayout = new VerticalLayout();

    verticalLayout.add(setupGrid());

    FlexBoxLayout content = new FlexBoxLayout(verticalLayout);

    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeightFull();
    content.setWidthFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);

    return content;
  }

  private Component addButtonDialog(){
    Button addStudentButton = new Button(VaadinIcon.PLUS.create());
    setupDialog();

    addStudentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    addStudentButton.addClickListener(e -> {
      currentUser = new User();
      SecureRandom secureRandom = new SecureRandom();
      currentUser.setPlainPw(secureRandom.toString());
      binder.readBean(currentUser);
      dialog.open();
    });

    return addStudentButton;
  }

  private Dialog setupDialog(){
    dialog = new Dialog();
    dialog.add(new Label("Schüler hinzufügen"));
    dialog.add(createForm(), createButtonLayout());

    binder.bindInstanceFields(this);
    binder.bind(firstName, "firstName");
    binder.bind(lastName, "lastName");
    binder.bind(username, "username");
    binder.bind(plainPw, "plainPw");
    binder.bind(email, "email");

    return dialog;
  }

  private FormLayout createForm(){
    formLayout = new FormLayout();
    String generatedUsername;
    role.setItems(Role.values());

    firstName.setRequired(true);
    lastName.setRequired(true);
    username.setEnabled(false);
    plainPw.setEnabled(false);

    formLayout.add(firstName, lastName, username, email, plainPw);
    return formLayout;
  }

  private HorizontalLayout createButtonLayout(){
    addClassName("button-layout");
    HorizontalLayout layout = new HorizontalLayout();
    layout.getStyle().set("margin-bottom", "20px");
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

    save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);

    save.addClickListener(e -> {
      firstLetter = String.valueOf(firstName.getValue().charAt(0));
      secondLetters = lastName.getValue().charAt(0) +
          String.valueOf(lastName.getValue().charAt(1));
      generatedUsername = firstLetter+secondLetters;
      username.setValue(generatedUsername);
      if (
        binder.isValid()){
        saveStudent(currentUser);
        dialog.close();
      }
    });
    cancel.addClickListener(e -> {dialog.close();});

    layout.add(save, cancel);
    return layout;
  }

  private void saveStudent(User user) {
    try{
      binder.writeBean(currentUser);

      String plainPw = user.getPlainPw();
      user.setPassword(passwordEncoder.encode(plainPw));
      user.setRole(Role.STUDENT);
      userService.save(user);
      students.add(user);
      findAllStudents();

    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private Component setupGrid(){
    grid = new Grid<>(User.class, false);
    grid.addClassName("student-grid");
    grid.getElement().getStyle().set("heigt", "auto");

    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.addColumn(new ComponentRenderer<>(this::createFirstName))
        .setHeader("Vorname")
        .setResizable(true);

    grid.addColumn(new ComponentRenderer<>(this::createLastName))
        .setHeader("Beschreibung")
        .setResizable(true);

    grid.addColumn(new ComponentRenderer<>(this::createEmail))
        .setHeader("Email")
        .setResizable(true);

    grid.addColumn(new ComponentRenderer<>(this::createUsername))
        .setHeader("Benutzername")
        .setResizable(true);

    grid.addColumn(new ComponentRenderer<>(this::createDeleteButton))
        .setHeader("")
        .setResizable(true);

    students = createStudentList();
    GridListDataView<User> dataView = grid.setItems(students);
    searchField = new TextField();
    searchField.setWidth("50%");
    searchField.setPlaceholder("Suche");
    searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
    searchField.setValueChangeMode(ValueChangeMode.EAGER);
    searchField.addValueChangeListener(evt -> dataView.refreshAll());

    dataView.addFilter(student -> {
      String searchTerm = searchField.getValue().trim();

      if (searchTerm.isEmpty())
        return true;

      boolean matchesFullName = matchesTerm(student.getFirstName(), searchTerm);
      boolean matchesEmail = matchesTerm(student.getEmail(), searchTerm);
      boolean matchesLastName = matchesTerm(student.getLastName(), searchTerm);
      boolean matchesUsername = matchesTerm(student.getUsername(), searchTerm);

      return matchesFullName || matchesEmail || matchesLastName || matchesUsername;
    });

    HorizontalLayout bar = new HorizontalLayout(searchField, addButtonDialog());
    VerticalLayout layout = new VerticalLayout(bar, grid);
    layout.setPadding(false);

    return layout;
  }

  private Component createFirstName(User user){
    return new Label(user.getFirstName() != null ? user.getFirstName() : "");
  }

  private Component createLastName(User user){
    return new Label(user.getLastName() != null ? user.getLastName() : "");
  }

  private Component createEmail(User user){
    return new Label(user.getEmail() != null ? user.getEmail() : "");
  }

  private Component createUsername(User user){
    return new Label(user.getUsername() != null ? user.getUsername() : "");
  }

  private Component createDeleteButton(User user){
    Button delete = new Button(VaadinIcon.TRASH.create());
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    delete.addClickListener(e -> {
      students.remove(user);
      userService.delete(user);
      findAllStudents();
    });
    return delete;
  }

  protected List<User> executeFindAllUsers() {
    return userService.findAll();
  }

  void findAllStudents() {
    Collection<User> content = new ArrayList<>();
    content = executeFindAllUsers();

    fillUserGrid(content);
  }

  private void fillUserGrid(Collection<User> content) {
    ListDataProvider<User> userDataProviderListener;
    if(content.size()==0){
      userDataProviderListener = DataProvider.ofCollection(new ArrayList<>());
      grid.setDataProvider(userDataProviderListener);
    } else {
      userDataProviderListener = DataProvider.ofCollection(content);
      grid.setDataProvider(userDataProviderListener);
    }
  }

  private List<User> createStudentList(){
    List<User> allUsers = executeFindAllUsers();
    List<User> students = new ArrayList<>();
    for (int i = 0; i < allUsers.size(); i++) {
      if (allUsers.get(i).getRole().equals(Role.STUDENT)){
        students.add(allUsers.get(i));
      }
    }

    return students;
  }

  private void refreshGrid() {
    findAllStudents();
  }

  private boolean matchesTerm(String value, String searchTerm) {
    return value.toLowerCase().contains(searchTerm.toLowerCase());
  }
}
