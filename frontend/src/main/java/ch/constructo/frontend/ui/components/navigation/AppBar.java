package ch.constructo.frontend.ui.components.navigation;

import ch.constructo.frontend.ui.components.FlexBoxLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;

public class AppBar extends FlexBoxLayout {

  private FlexBoxLayout container;

  private Button menuIcon;
  private Button contextIcon;

  private H4 title;

  public AppBar(String title) {
    initTitle(title);
  }

  private void initTitle(String title) {
    this.title = new H4(title);
    this.title.setClassName("appBar_title");
  }

  public void reset() {
    title.setText("");
  }

}
