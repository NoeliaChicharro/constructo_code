package ch.constructo.master.data.services.impl;

import ch.constructo.master.data.entities.ConstructionStep;
import ch.constructo.master.data.repositories.ConstructionStepRepository;
import ch.constructo.master.data.services.ConstructionStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstructionStepServiceImpl implements ConstructionStepService {

  private static final Logger log = LoggerFactory.getLogger(ConstructionStepServiceImpl.class);

  @Autowired
  private ConstructionStepRepository repository;

  @Override
  public ConstructionStep save(ConstructionStep s) {
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
  public ConstructionStep findOne(Long aLong) {
    return null;
  }

  @Override
  public void delete(ConstructionStep object) {
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
  public List<ConstructionStep> findAll() {
    return (List<ConstructionStep>) repository.findAll();
  }

  @Override
  public List<ConstructionStep> findAll(List<Long> iterable) {
    return (List<ConstructionStep>) repository.findAllById(iterable);
  }

  @Override
  public void delete(List<ConstructionStep> iterable) {
    repository.deleteAll(iterable);
  }

  @Override
  public List<ConstructionStep> save(Iterable<ConstructionStep> iterable) {
    return (List<ConstructionStep>) repository.saveAll(iterable);
  }
}
