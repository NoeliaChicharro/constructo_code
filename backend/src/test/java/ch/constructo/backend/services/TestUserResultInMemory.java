package ch.constructo.backend.services;

import ch.constructo.backend.config.DbTestCaseConfig;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.entities.UserResult;
import ch.constructo.backend.data.enums.GarmentType;
import ch.constructo.backend.data.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DbTestCaseConfig
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserResultInMemory {

  @Autowired
  private UserResultService userResultService;

  @Autowired
  private GarmentService garmentService;

  @Autowired
  private UserService userService;

  @Test
  public void test01Save(){
    UserResult userResult = new UserResult();
    userResult.setUser(createUser());
    userResult.setGarment(createGarment());
    userResult.setPassed(true);
    userResult.setRightAmount(10);

    UserResult saved = userResultService.save(userResult);
    assertNotNull("UserResult not saved ", saved);
    assertEquals("Id does not match ", Long.valueOf(1), saved.getId());
  }

  @Test
  public void test02deleteUserResult(){
    userResultService.deleteAll();
  }

  @Test
  public void test04findUserResults() {
    createUserResultTwo();
    List<UserResult> userResults = userResultService.findAll();

    assertNotNull("Garments not found ", userResults);
    assertEquals("Id does not match ", 1, userResults.size());
  }

  public UserResult createUserResult(){
    UserResult userResult = new UserResult();
    userResult.setUser(createUser());
    userResult.setGarment(createGarment());
    userResult.setPassed(true);
    userResult.setRightAmount(10);

    return userResultService.save(userResult);
  }

  public UserResult createUserResultTwo(){
    UserResult userResult = new UserResult();
    userResult.setUser(createUser());
    userResult.setGarment(createGarment());
    userResult.setPassed(false);
    userResult.setRightAmount(2);

    return userResultService.save(userResult);
  }

  public Garment createGarment(){
    Garment garment = new Garment();
    garment.setName("Blouse");
    garment.setGarmentCategory(GarmentType.BLOUSE);
    garment.setDescription("This is a test");

    return garmentService.save(garment);
  }

  public User createUser(){
    User user = new User();
    user.setFirstName("Noelia");
    user.setLastName("Chicharro");
    user.setUsername("nch");
    user.setEmail("noelia@email.ch");
    user.setPassword("password");
    user.setRole(Role.ADMIN);

    return userService.save(user);
  }
}
