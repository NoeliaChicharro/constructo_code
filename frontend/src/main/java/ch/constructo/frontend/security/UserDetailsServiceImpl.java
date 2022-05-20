package ch.constructo.frontend.security;

import ch.constructo.master.data.entities.User;
import ch.constructo.master.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // ->> TODO remove it if you work with local database !!!!!
/*    if(username.equalsIgnoreCase("admin")){
      User user = userService.findByUsername(username);
      if (null == user) {
        User admin = new User();
        admin.setEmail("admin@mail.com");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin")); // set default password
        admin.setFirstName("-");
        admin.setLastName("-");
        user = userService.save(admin);
      }
      return new UserDetailsImpl(user);
    }*/
    // <<-

    User user = userService.findByUsername(username);
    if (null == user) {
      throw new UsernameNotFoundException("No user present with username: " + username);
    } else {
      UserDetails userDetails = new UserDetailsImpl(user);

      return userDetails;
    }
  }
}
