package ch.constructo.master.data.services;

import ch.constructo.master.data.config.DbTestCaseConfig;
import ch.constructo.master.data.entities.User;
import ch.constructo.master.data.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DbTestCaseConfig
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserInMemorie {

  @Autowired
  private UserService userService;

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
}
