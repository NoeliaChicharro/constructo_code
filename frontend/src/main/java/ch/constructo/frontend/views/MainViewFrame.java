package ch.constructo.frontend.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PreserveOnRefresh
public class MainViewFrame extends Composite<Div> implements HasStyle{
  private static final Logger log = LoggerFactory.getLogger(MainViewFrame.class);

  private String CLASS_NAME = "view-frame";

  private Div header;

  public VerticalLayout wrapper;
  private Div content;
  private Div footer;

  public MainViewFrame() {
    setClassName(CLASS_NAME);

    header = new Div();
    header.setClassName(CLASS_NAME + "__header");

    wrapper = new VerticalLayout();
    wrapper.setClassName(CLASS_NAME + "__wrapper");

    content = new Div();
    content.setClassName(CLASS_NAME + "__content");

    footer = new Div();
    footer.setClassName(CLASS_NAME + "__footer");

    wrapper.add(content);

    content.getStyle().set("width", "100%");
    content.getStyle().set("flex", "1 1 100%");
    content.setWidthFull();


    getContent().add(header, wrapper, footer);
  }

  public void setViewHeader(Component... components) {
    header.removeAll();
    header.add(components);
  }

  public void setViewContent(Component... components) {
    content.removeAll();
    content.add(components);
  }

  public void setViewFooter(Component... components) {
    footer.removeAll();
    footer.add(components);
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    MainLayout.get().getAppBar().reset();
  }

  @Override
  protected void onDetach(DetachEvent detachEvent) {
    super.onDetach(detachEvent);
  }

  public Div getContentPane() {
    return content;
  }

  public VerticalLayout getWrapper() {
    return wrapper;
  }

  public enum Position {
    RIGHT, BOTTOM
  }
}
