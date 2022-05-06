package ch.constructo.frontend.security;

import ch.constructo.frontend.ui.exceptions.AccessDeniedExceptionHandler;
import ch.constructo.frontend.ui.exceptions.PortalInternalServerError;
import ch.constructo.frontend.views.login.LoginView;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class SecurityUtils {

  public SecurityUtils() {
    // Util Methods only
  }

  /**
   * @param request {@link HttpServletRequest}
   * @return true if is an internal framework request. false otherwise
   */
  static boolean isFrameworkInternalRequest(HttpServletRequest request) {
/*
    final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
    return parameterValue != null
        && Stream.of(ServletHelper.RequestType.values())
        .anyMatch(r -> r.getIdentifier().equals(parameterValue));
*/
    final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
    return parameterValue != null
        && Stream.of(HandlerHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
  }

  /**
   * Tests if the user is autheticated.
   */
  public static boolean isUserLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken)
        && authentication.isAuthenticated();
  }

  public static String getCurrentLoggedUserId(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication.getName();
  }


  public static boolean isAccessGranted(Class<?> securedClass) {
    final boolean publicView =
        LoginView.class.equals(securedClass)
           // || RegistrationView.class.equals(securedClass)

            || AccessDeniedExceptionHandler.class.equals(securedClass)
            || PortalInternalServerError.class.equals(securedClass)

        ;

    // Always allow access to public views
    if (publicView) {
      return true;
    }

    // All other views require authentication
    if (!isUserLoggedIn()) {
      return false;
    }

    // Allow if no roles are required.
    Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
    if (secured == null) {
      return false;
    }
    Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();

    // lookup needed role in user roles
    List<String> allowedRoles = Arrays.asList(secured.value());
    return userAuthentication.getAuthorities().stream() //
        .map(GrantedAuthority::getAuthority)
        .anyMatch(allowedRoles::contains);

  }
}
