package ch.constructo.frontend.security;

import ch.constructo.backend.data.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails, Serializable {

  private String username;
  private String password;
  private boolean enabled;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;

  private User user;

  private static final long serialVersionUID = 1L;
  public static final String ROLE = "ROLE_";
  protected static final GrantedAuthority ROLE_NONE = new GrantedAuthority() {
    private static final long serialVersionUID = 1L;
    private final String ROLE_NONE_NAME = ROLE + "NONE";

    @Override
    public String getAuthority() {
      return ROLE_NONE_NAME;
    }
  };

  protected static final GrantedAuthority ROLE_ADMIN = new GrantedAuthority() {
    private static final long serialVersionUID = 1L;
    private final String ROLE_ADMIN_NAME = ROLE + "ADMIN";

    @Override
    public String getAuthority() {
      return ROLE_ADMIN_NAME;
    }
  };

  protected static final GrantedAuthority ROLE_USER = new GrantedAuthority() {

    private static final long serialVersionUID = 1L;
    private final String ROLE_USER_NAME = ROLE + "USER";

    @Override
    public String getAuthority() {
      return ROLE_USER_NAME;
    }

  };

  protected static final GrantedAuthority[] AUTHORITIES = new GrantedAuthority[]{
      ROLE_NONE, ROLE_ADMIN, ROLE_USER
  };
  private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

  static {
  }

  public UserDetailsImpl(User user) {
    this.user = user;

    init();

    switch (user.getRole()){
      case ADMIN:
        authorities.add(ROLE_ADMIN);
      case STUDENT:
        authorities.add(ROLE_USER);
    }
  }


  private void init() {

    setUsername(user.getUsername());
    setPassword(user.getPassword());
    setEnabled(true);       //@TODO implement correct state here if user contains a user state

    setAccountNonLocked(true);
    setAccountNonExpired(true);
    setCredentialsNonExpired(true);
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return username;
  }


  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }


  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasRole(String role) {
    for (GrantedAuthority grantedAuthority : getAuthorities()) {
      if (grantedAuthority.getAuthority().equals(role)) {
        return true;
      }
    }

    return false;
  }

  public User getUser() {
    return user;
  }
}
