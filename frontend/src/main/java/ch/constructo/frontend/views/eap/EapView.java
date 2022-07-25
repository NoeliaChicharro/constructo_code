package ch.constructo.frontend.views.eap;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.services.GarmentService;
import ch.constructo.frontend.security.SecurityUtils;
import ch.constructo.frontend.ui.components.FlexBoxLayout;
import ch.constructo.frontend.ui.components.navigation.AppBar;
import ch.constructo.frontend.ui.layout.Horizontal;
import ch.constructo.frontend.ui.layout.Top;
import ch.constructo.frontend.ui.util.BoxSizing;
import ch.constructo.frontend.views.MainLayout;
import ch.constructo.frontend.views.MainViewFrame;
import ch.constructo.frontend.views.dashboard.DashboardView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import com.vaadin.flow.component.Component;

import java.util.*;


@PageTitle("Epa")
@Route(value = "epa", layout = MainLayout.class)
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class EapView extends MainViewFrame {

  @Autowired
  private GarmentService garmentService;

  private Grid<Garment> grid;
  private ListDataProvider<Garment> garmentListDataProvider;

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
    grid.getElement().getStyle().set("heigt", "auto");

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

    grid.asSingleSelect().addValueChangeListener(e -> {
      if(grid.getSelectionModel().getFirstSelectedItem().isPresent()){
        rerouteToSelectedGarment(grid.getSelectionModel().getFirstSelectedItem().get());
      }
    });

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

  private void rerouteToSelectedGarment(Garment garment){
    Garment found = garmentService.findOne(garment.getId());
    if (found != null && SecurityUtils.isUserLoggedIn()){
      UI.getCurrent().navigate(ConstructionView.class);
      // @TODO: id to next view
    }
  }

  void findAllGarments() {

    Collection<Garment> content = new ArrayList<>();
    content = executeFindAllGarments();

    fillGarmentGrid(content);
  }

  private void fillGarmentGrid(Collection<Garment> content) {
    if(content.size()==0){
      garmentListDataProvider = DataProvider.ofCollection(new ArrayList<>());
      grid.setDataProvider(garmentListDataProvider);
    } else {
      garmentListDataProvider = DataProvider.ofCollection(content);
      grid.setDataProvider(garmentListDataProvider);
    }
  }

  private void refreshGrid() {
    findAllGarments();
  }

  protected List<Garment> executeFindAllGarments() {
    return garmentService.findAll();
  }

}
