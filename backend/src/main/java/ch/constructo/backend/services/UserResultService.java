package ch.constructo.backend.services;

import ch.constructo.backend.entities.Garment;
import ch.constructo.backend.entities.UserResult;

public interface UserResultService extends BaseDataService<UserResult> {

  UserResult findByGarment(Garment garment);

  UserResult findByPassed(boolean passed);
}
