package ch.constructo.backend.services;

import ch.constructo.backend.config.DbTestCaseConfig;
import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DbTestCaseConfig
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserInMemory {

  @Autowired
  private UserService userService;

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  public void test01Save(){

    User user = new User();
    user.setFirstName("Noelia");
    user.setLastName("Chicharro");
    user.setUsername("nch");
    user.setEmail("noelia@email.ch");
    user.setPassword("password");
    user.setRole(Role.ADMIN);

    User saved = userService.save(user);
    assertNotNull("User not stored in DB ", saved);
    assertEquals("Id does not match ", Long.valueOf(1), saved.getId());
  }

  @Test
  public void test02deleteUser(){
    userService.deleteAll();
  }


  @Test
  public void test03findUser(){
    createUser();
    User found = userService.findByUsername("nch");

    assertNotNull("User not found ", found);
    assertEquals("Wrong user found ", "nch", found.getUsername());
  }

  @Test
  public void test04findUsers() {
    createUserTwo();
    List<User> users = userService.findAll();

    assertNotNull("Users not found ", users);
    assertEquals("Amount does not match ", 2, users.size());
  }

  public User createUser(){
    User user = new User();
    user.setFirstName("Noelia");
    user.setLastName("Chicharro");
    user.setUsername("nch");
    user.setEmail("noelia@email.ch");
    user.setPassword(passwordEncoder.encode("test"));
    System.out.println(user.getPassword());
    user.setRole(Role.ADMIN);

    return userService.save(user);
  }

  public User createUserTwo(){
    User user = new User();
    user.setFirstName("Norbert");
    user.setLastName("Piskorz");
    user.setUsername("npi");
    user.setEmail("norbert@email.ch");
    user.setPassword("password");
    user.setRole(Role.TEACHER);

    return userService.save(user);
  }
}
