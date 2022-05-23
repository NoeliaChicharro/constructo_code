package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.ConstructionStep;

public interface ConstructionStepService extends BaseDataService<ConstructionStep>{
  ConstructionStep findByText(String s);
}
