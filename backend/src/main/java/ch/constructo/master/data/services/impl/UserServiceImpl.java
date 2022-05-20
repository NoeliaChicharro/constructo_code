package ch.constructo.master.data.services.impl;

import ch.constructo.master.data.entities.User;
import ch.constructo.master.data.repositories.UserRepository;
import ch.constructo.master.data.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository repository;

  @Override
  public User save(User s) {
    return repository.save(s);
  }

  @Override
  public void delete(Long aLong) {
    repository.deleteById(aLong);
  }

  @Override
  public boolean exists(Long aLong) {
    return repository.existsById(aLong);
  }

  @Override
  public User findOne(Long aLong) {
    return null;
  }

  @Override
  public void delete(User object) {
    repository.delete(object);
  }

  @Override
  public long count() {
    return repository.count();
  }

  @Override
  public void deleteAll() {
    repository.deleteAll();
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public List<User> findAll(List<Long> iterable) {
    return (List<User>) repository.findAllById(iterable);
  }

  @Override
  public void delete(List<User> iterable) {
    repository.deleteAll(iterable);
  }

  @Override
  public List<User> save(Iterable<User> iterable) {
    return (List<User>) repository.saveAll(iterable);
  }

  @Override
  public User findByUsername(String username) {
    return repository.findByUsername(username);
  }
}
