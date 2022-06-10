package ch.constructo.backend.services.impl;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.enums.StepType;
import ch.constructo.backend.services.ConstructionStepService;
import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.repositories.ConstructionStepRepository;
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
    return repository.findOne(aLong);
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

  @Override
  public ConstructionStep findByText(String s){
    return repository.findByText(s);
  }

  @Override
  public List<ConstructionStep> findByGarment(Garment garment) {return repository.findByGarment(garment);}

  @Override
  public List<ConstructionStep> findAllByStepType(StepType stepType) {return repository.findAllByStepType(stepType);}
}
