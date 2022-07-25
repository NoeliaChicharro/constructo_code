package ch.constructo.frontend.views;

import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.enums.Role;
import ch.constructo.backend.services.UserService;
import ch.constructo.frontend.security.SecurityUtils;
import ch.constructo.frontend.ui.components.navigation.AppBar;
import ch.constructo.frontend.views.dashboard.DashboardView;
import ch.constructo.frontend.views.eap.EapView;
import ch.constructo.frontend.views.login.LoginView;
import ch.constructo.frontend.views.teachers.CreateEap;
import ch.constructo.frontend.views.teachers.StudentListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

  private AppBar appBar;

  @Autowired
  private UserService userService;

  /**
   * A simple navigation item component, based on ListItem element.
   */
  public static class MenuItemInfo extends ListItem {

    private final Class<? extends Component> view;

    public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
      this.view = view;
      RouterLink link = new RouterLink();
      link.addClassNames("menu-item-link");
      link.setRoute(view);

      Span text = new Span(menuTitle);
      text.addClassNames("menu-item-text");

      link.add(new LineAwesomeIcon(iconClass), text);
      add(link);
    }

    public Class<?> getView() {
      return view;
    }

    /**
     * Simple wrapper to create icons using LineAwesome iconset. See
     * https://icons8.com/line-awesome
     */
    @NpmPackage(value = "line-awesome", version = "1.3.0")
    public static class LineAwesomeIcon extends Span {
      public LineAwesomeIcon(String lineawesomeClassnames) {
        addClassNames("menu-item-icon");
        if (!lineawesomeClassnames.isEmpty()) {
          addClassNames(lineawesomeClassnames);
        }
      }
    }

  }

  private H1 viewTitle;

  public MainLayout(UserService userService) {
    this.userService = userService;
    setPrimarySection(Section.DRAWER);
    addToNavbar(true, createHeaderContent());
    addToDrawer(createDrawerContent());
  }

  private Component createHeaderContent() {
    DrawerToggle toggle = new DrawerToggle();
    toggle.addClassNames("view-toggle");
    toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    toggle.getElement().setAttribute("aria-label", "Menu toggle");

    User user = userService.findByUsername(SecurityUtils.getCurrentLoggedUserId());
    String name = user.getFirstName() + " " + user.getLastName();
    Avatar avatarName = new Avatar(name);
    HorizontalLayout avatarButton = new HorizontalLayout(avatarName);
    avatarButton.setAlignItems(FlexComponent.Alignment.CENTER);
    avatarButton.addClickListener(e -> {
      createProfileDialog();
    });

    appBar = new AppBar("");

    viewTitle = new H1();
    viewTitle.addClassNames("view-title");

    HorizontalLayout titleContainer = new HorizontalLayout(toggle, viewTitle);
    titleContainer.getStyle().set("width", "95%");
    titleContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
    titleContainer.setAlignItems(FlexComponent.Alignment.CENTER);
    Header header = new Header(titleContainer, avatarButton);
    header.addClassNames("view-header");
    return header;
  }

  private Dialog createProfileDialog(){
    Dialog dialog = new Dialog();
    Button cancel = new Button("Schliessen");
    Button logout = new Button("Logout");

    logout.addClickListener(buttonClickEvent -> {
      dialog.close();
      SecurityContextHolder.clearContext();
      VaadinSession current = VaadinSession.getCurrent();
      if (current != null) {
        try {
          current.getSession().invalidate();
          current.close();
        } catch (Exception closeSessionException) {
          // do nothing
        } finally {
          getUI().ifPresent((ui ->
              ui.navigate(LoginView.class))
          );
        }
      }
    });

    FormLayout formLayout = new FormLayout();
    Label title = new Label("Passwort ändern");
    TextField password = new TextField("Neues Passwort");
    TextField password2 = new TextField("Passwort wiederholen");
    Button save = new Button("Speichern");
    save.addClickListener(e -> {
      if (password.getValue().equals(password2.getValue())){
        User user = userService.findByUsername(SecurityUtils.getCurrentLoggedUserId());
        user.setPassword(password.getValue());
        //@todo: passwort entcoder!!
        userService.save(user);
      } else {
        password.setErrorMessage("Passwörter stimmen nicht überein");
        password.setValue("");
        password2.setValue("");
        password.isAutofocus();
      }
    });
    formLayout.add(title, password, password2, save);

    HorizontalLayout buttonLayout = new HorizontalLayout(cancel, logout);
    buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

    dialog.add(formLayout, buttonLayout);
    cancel.addClickShortcut(Key.ESCAPE);
    cancel.addClickListener(e -> {
      dialog.close();
    });

    dialog.open();
    return dialog;
  }

  private Component createDrawerContent() {
    H2 appName = new H2("Constructo");
    appName.addClassNames("app-name");

    com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
        createNavigation(), createFooter());
    section.addClassNames("drawer-section");
    return section;
  }

  private Nav createNavigation() {
    Nav nav = new Nav();
    nav.addClassNames("menu-item-container");
    nav.getElement().setAttribute("aria-labelledby", "views");

    // Wrap the links in a list; improves accessibility
    UnorderedList list = new UnorderedList();
    list.addClassNames("navigation-list");
    nav.add(list);

    for (MenuItemInfo menuItem : createMenuItems()) {
      list.add(menuItem);

    }
    return nav;
  }

  private MenuItemInfo[] createMenuItems() {
    User user = userService.findByUsername(SecurityUtils.getCurrentLoggedUserId());
    if (user.getRole() == Role.ADMIN){
      return new MenuItemInfo[]{
          new MenuItemInfo("Dashboard", "la la-home", DashboardView.class),
          new MenuItemInfo("Studenten Liste", "la la-user", StudentListView.class),
          new MenuItemInfo("Eap", "la la-file", EapView.class),
          new MenuItemInfo("Eap Erstellen", "la la-globe", CreateEap.class)
      };
    }
    return new MenuItemInfo[]{
        new MenuItemInfo("Dashboard", "la la-home", DashboardView.class),
        new MenuItemInfo("Eap", "la la-file", EapView.class),
    };
  }

  private Footer createFooter() {
    Footer layout = new Footer();
    layout.addClassNames("footer");

    return layout;
  }

  @Override
  protected void afterNavigation() {
    super.afterNavigation();
    viewTitle.setText(getCurrentPageTitle());
  }

  private String getCurrentPageTitle() {
    PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
    return title == null ? "" : title.value();
  }

  public static MainLayout get() {
    Optional<Component> first = UI.getCurrent().getChildren()
        .filter(component -> component.getClass() == MainLayout.class)
        .findFirst();
    if(first.isPresent()){
      return (MainLayout)first.get();
    }
    return null; //UI.getCurrent().navigate(MainView.class);
  }

  public AppBar getAppBar() {
    return appBar;
  }
}


