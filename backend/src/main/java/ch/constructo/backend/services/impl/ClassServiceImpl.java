package ch.constructo.backend.services.impl;

import ch.constructo.backend.data.entities.Class;
import ch.constructo.backend.data.repositories.ClassRepository;
import ch.constructo.backend.services.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

  private static final Logger log = LoggerFactory.getLogger(ClassServiceImpl.class);

  @Autowired
  private ClassRepository repository;

  @Override
  public Class save(Class s) {
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
  public Class findOne(Long aLong) {
    return repository.findOne(aLong);
  }

  @Override
  public void delete(Class object) {
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
  public List<Class> findAll() {
    return (List<Class>) repository.findAll();
  }

  @Override
  public List<Class> findAll(List<Long> iterable) {
    return (List<Class>) repository.findAllById(iterable);
  }

  @Override
  public void delete(List<Class> iterable) {
    repository.deleteAll(iterable);
  }

  @Override
  public List<Class> save(Iterable<Class> iterable) {
    return (List<Class>) repository.saveAll(iterable);
  }

  @Override
  public Class findByName(String name) {
    return repository.findByName(name);
  }
}
