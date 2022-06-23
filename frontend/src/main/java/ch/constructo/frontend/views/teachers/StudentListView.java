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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle("Studenten Liste")
@Route(value = "studentListView", layout = MainLayout.class)
@Secured({"ROLE_ADMIN"})
public class StudentListView extends MainViewFrame {

  @Autowired
  protected UserService userService;

  private ListDataProvider<User> userDataProviderListener;

  //protected PatternForm form;
  private Grid<User> grid;
  protected TextField searchField = new TextField();
  protected Button addStudentButton = new Button();
  List<User> students;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initAppBar();
    executeFindAllUsers();
    setViewContent(createContent());
    //refreshGrid();
    //filter();
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

    students = createStudentList();
    GridListDataView<User> dataView = grid.setItems(students);
    searchField = new TextField();
    searchField.setWidth("50%");
    searchField.setPlaceholder("Nach Schüler suchen");
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

    VerticalLayout layout = new VerticalLayout(searchField, grid);
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

  protected List<User> executeFindAllUsers() {
    return userService.findAll();
  }

  void findAllStudents() {
    Collection<User> content = new ArrayList<>();
    content = executeFindAllUsers();

    fillUserGrid(content);
  }

  private void fillUserGrid(Collection<User> content) {
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
