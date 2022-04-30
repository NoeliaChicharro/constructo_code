package ch.constructo.frontend.ui.exceptions;

import ch.constructo.frontend.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@Tag(Tag.DIV)
@ParentLayout(MainLayout.class)
public class AccessDeniedExceptionHandler extends Component implements HasErrorParameter<AccessDeniedException> {

  @Override
  public int setErrorParameter(BeforeEnterEvent event,
                               ErrorParameter<AccessDeniedException>
                                   parameter) {

    Element logInfo = ElementFactory
        .createDiv( getTranslation("accessDenied"));
    logInfo.getStyle().set("marginLeft", "10px");
    logInfo.getStyle().set("marginTop", "10px");
    logInfo.getStyle().set("marginBottom", "10px");
    logInfo.getStyle().set("fontWeight", "bold");
    logInfo.getStyle().set("color", "#6495ED");
    getElement().appendChild(logInfo);

    return HttpServletResponse.SC_FORBIDDEN;
  }
}
