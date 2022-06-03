package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.entities.UserResult;

public interface UserResultService extends BaseDataService<UserResult> {

  UserResult findByGarment(Garment garment);

  UserResult findByPassed(boolean passed);

  UserResult findByUser(String username);
}
