package ch.constructo.master.data.services;

import ch.constructo.master.data.entities.User;

public interface UserService extends BaseDataService<User> {

  User findByUsername(String username);
}
