package ch.constructo.backend.services;

import ch.constructo.backend.entities.User;

public interface UserService extends BaseDataService<User> {

  User findByUsername(String username);
}
