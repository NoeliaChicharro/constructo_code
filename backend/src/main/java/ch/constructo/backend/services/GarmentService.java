package ch.constructo.backend.services;

import ch.constructo.backend.entities.Garment;
import ch.constructo.backend.enums.GarmentType;

public interface GarmentService extends BaseDataService<Garment>{

  Garment findByName(String name);

  Garment findByGarmentType(GarmentType garmentType);
}
