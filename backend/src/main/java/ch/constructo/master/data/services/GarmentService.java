package ch.constructo.master.data.services;

import ch.constructo.master.data.entities.Garment;
import ch.constructo.master.data.enums.GarmentType;

public interface GarmentService extends BaseDataService<Garment>{

  Garment findByName(String name);

  Garment findByGarmentType(GarmentType garmentType);
}
