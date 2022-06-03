package ch.constructo.backend.services.impl;

import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.services.UserResultService;
import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.UserResult;
import ch.constructo.backend.data.repositories.UserResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResultServiceImpl implements UserResultService {

  private static final Logger log = LoggerFactory.getLogger(UserResultServiceImpl.class);

  @Autowired
  private UserResultRepository repository;

  @Override
  public UserResult save(UserResult s) {
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
  public UserResult findOne(Long aLong) {
    return repository.findOne(aLong);
  }

  @Override
  public void delete(UserResult object) {
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
  public List<UserResult> findAll() {
    return repository.findAll();
  }

  @Override
  public List<UserResult> findAll(List<Long> iterable) {
    return (List<UserResult>) repository.findAllById(iterable);
  }

  @Override
  public void delete(List<UserResult> iterable) {
    repository.deleteAll(iterable);
  }

  @Override
  public List<UserResult> save(Iterable<UserResult> iterable) {
    return (List<UserResult>) repository.saveAll(iterable);
  }

  @Override
  public UserResult findByGarment(Garment garment) {
    return repository.findByGarment(garment);
  }

  @Override
  public UserResult findByPassed(boolean passed) {
    return repository.findByPassed(passed);
  }

  @Override
  public UserResult findByUser(String username){return repository.findByUser(username);}
}
