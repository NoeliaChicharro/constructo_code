package ch.constructo.backend.services.impl;

import ch.constructo.backend.entities.Garment;
import ch.constructo.backend.enums.GarmentType;
import ch.constructo.backend.repositories.GarmentRepository;
import ch.constructo.backend.services.GarmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarmentServiceImpl implements GarmentService {

  private static final Logger log = LoggerFactory.getLogger(GarmentServiceImpl.class);

  @Autowired
  private GarmentRepository repository;

  @Override
  public Garment save(Garment s) {
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
  public Garment findOne(Long aLong) {
    return null;
  }

  @Override
  public void delete(Garment object) {
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
  public List<Garment> findAll() {
    return repository.findAll();
  }

  @Override
  public List<Garment> findAll(List<Long> iterable) {
    return (List<Garment>) repository.findAllById(iterable);
  }

  @Override
  public void delete(List<Garment> iterable) {
    repository.deleteAll(iterable);
  }

  @Override
  public List<Garment> save(Iterable<Garment> iterable) {
    return (List<Garment>) repository.saveAll(iterable);
  }

  @Override
  public Garment findByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public Garment findByGarmentType(GarmentType garmentType) {
    return repository.findByGarmentType(garmentType);
  }
}
