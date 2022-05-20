package ch.constructo.master.data.services;

import ch.constructo.master.data.entities.Garment;
import ch.constructo.master.data.entities.UserResult;

public interface UserResultService extends BaseDataService<UserResult> {

  UserResult findByGarment(Garment garment);

  UserResult findByPassed(boolean passed);
}
