package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.enums.Role;

import java.util.List;

public interface UserService extends BaseDataService<User> {

  User findByUsername(String username);

  List<User> findByRole(Role role);
}
