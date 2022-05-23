package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.User;

public interface UserService extends BaseDataService<User> {

  User findByUsername(String username);
}
