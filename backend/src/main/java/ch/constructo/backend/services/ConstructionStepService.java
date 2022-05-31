package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;

public interface ConstructionStepService extends BaseDataService<ConstructionStep>{
  ConstructionStep findByText(String s);

  ConstructionStep findByGarment(Garment garment);
}
