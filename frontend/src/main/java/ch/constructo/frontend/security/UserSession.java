package ch.constructo.frontend.security;

import ch.constructo.backend.data.entities.User;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.io.Serializable;

public class UserSession implements CurrentUser, Serializable, HttpSessionBindingListener {

  // key for the session user
  public static final String SESSION_USER = "sessionUser";

  private static final long serialVersionUID = 1L;

  private transient UserDetailsImpl userDetails;

  public UserSession(UserDetailsImpl userDetail) {
    this.userDetails = userDetail;
  }

  public UserDetailsImpl getUserDetail() {
    return userDetails;
  }

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    //throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    //throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public User getUser() {
    return userDetails.getUser();
  }
}
