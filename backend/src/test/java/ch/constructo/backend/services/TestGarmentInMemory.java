package ch.constructo.backend.services;

import ch.constructo.backend.config.DbTestCaseConfig;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.enums.GarmentType;
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
public class TestGarmentInMemory {

  @Autowired
  private GarmentService garmentService;

  /**
   * <p>Test Save</p>
   */
  @Test
  public void test01saveGarment(){

    Garment garment = new Garment();
    garment.setName("Blouse");
    garment.setGarmentCategory(GarmentType.BLOUSE);
    garment.setDescription("This is a test");

    Garment saved = garmentService.save(garment);

    assertNotNull("Garment not saved ", saved);
    assertEquals("Id does not match ", Long.valueOf(1), saved.getId());
  }

  /**
   * <p>Test Delete</p>
   */
  @Test
  public void test02deleteGarment(){
    garmentService.deleteAll();
  }

  /**
   * <p>Test Find</p>
   */
  @Test
  public void test03findGarment(){
    createGarment();
    Garment found = garmentService.findByName("Blouse");

    assertNotNull("Garment not found ", found);
    assertEquals("Wrong garment found ", "Blouse", found.getName());
  }

  /**
   * <p>Test Find Multiple</p>
   */
  @Test
  public void test04findGarments() {
    createGarmentTwo();
    List<Garment> garments = garmentService.findAll();

    assertNotNull("Garments not found ", garments);
    assertEquals("Id does not match ", 2, garments.size());
  }

  @Test
  public void test05findByType(){
    Garment garment = garmentService.findByGarmentType(GarmentType.BLOUSE);

    assertNotNull("Users not found ", garment);
    assertEquals("Amount does not match ", "Blouse", garment.getName());
  }

  @Test
  public void test06findOne(){
    Garment garment = garmentService.findOne(3L);

    assertNotNull("Users not found ", garment);
    assertEquals("Amount does not match ", "Skirt", garment.getName());
  }

  public Garment createGarment(){
    Garment garment = new Garment();
    garment.setName("Blouse");
    garment.setGarmentCategory(GarmentType.BLOUSE);
    garment.setDescription("This is a test");

    return garmentService.save(garment);
  }

  public Garment createGarmentTwo(){
    Garment garment = new Garment();
    garment.setName("Skirt");
    garment.setDescription("This is a test for Skirt");

    return garmentService.save(garment);
  }


}
