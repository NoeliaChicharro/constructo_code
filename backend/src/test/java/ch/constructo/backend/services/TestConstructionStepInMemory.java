package ch.constructo.backend.services;

import ch.constructo.backend.config.DbTestCaseConfig;
import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.enums.GarmentType;
import ch.constructo.backend.data.enums.StepType;
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
public class TestConstructionStepInMemory {

  @Autowired
  private ConstructionStepService constructionStepService;

  @Autowired
  private GarmentService garmentService;

  /**
   * <p>Test Save</p>
   */
  @Test
  public void test01save(){
    ConstructionStep constructionStep = new ConstructionStep();
    constructionStep.setStepType(StepType.PREPARE);
    constructionStep.setText("Double fabric");
    constructionStep.setGarment(createGarment());

    ConstructionStep saved = constructionStepService.save(constructionStep);

    assertNotNull("ConstructionStep not saved ", saved);
    assertEquals("Id does not match ", Long.valueOf(1), saved.getId());
  }

  /**
   * <p>Test Delete</p>
   */
  @Test
  public void test02deleteConstructionStep(){
    constructionStepService.deleteAll();
  }


  /**
   * <p>Test Find</p>
   */
  @Test
  public void test03findConstructionStep(){
    createConstructionStep();
    ConstructionStep found = constructionStepService.findByText("Double fabric");

    assertNotNull("Garment not found ", found);
    assertEquals("Wrong garment found ", "Double fabric", found.getText());
  }

  /**
   * <p>Test Find Multiple</p>
   */
  @Test
  public void test04findConstructionSteps() {
    createConstructionStepTwo();
    List<ConstructionStep> constructionSteps = constructionStepService.findAll();

    assertNotNull("Garments not found ", constructionSteps);
    assertEquals("Id does not match ", 2, constructionSteps.size());
  }

  public Garment createGarment(){
    Garment garment = new Garment();
    garment.setName("Blouse");
    garment.setGarmentCategory(GarmentType.BLOUSE);
    garment.setDescription("This is a test");

    return garmentService.save(garment);
  }

  public ConstructionStep createConstructionStep(){
    ConstructionStep constructionStep = new ConstructionStep();
    constructionStep.setStepType(StepType.PREPARE);
    constructionStep.setText("Double fabric");
    constructionStep.setGarment(createGarment());

    return constructionStepService.save(constructionStep);
  }

  public ConstructionStep createConstructionStepTwo(){
    ConstructionStep constructionStep = new ConstructionStep();
    constructionStep.setStepType(StepType.FINISH);
    constructionStep.setText("Attach buttons");
    constructionStep.setGarment(createGarment());

    return constructionStepService.save(constructionStep);
  }
}
