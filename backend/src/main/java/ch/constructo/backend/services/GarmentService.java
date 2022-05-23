package ch.constructo.backend.services;

import ch.constructo.backend.data.enums.GarmentType;
import ch.constructo.backend.data.entities.Garment;

public interface GarmentService extends BaseDataService<Garment>{

  Garment findByName(String name);

  Garment findByGarmentType(GarmentType garmentType);
}
