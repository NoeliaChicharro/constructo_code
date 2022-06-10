package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.enums.StepType;

import java.util.List;

public interface ConstructionStepService extends BaseDataService<ConstructionStep>{
  ConstructionStep findByText(String s);

  List<ConstructionStep> findByGarment(Garment garment);

  List<ConstructionStep> findAllByStepType(StepType stepType);
}
