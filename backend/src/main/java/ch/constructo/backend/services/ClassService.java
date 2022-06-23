package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.Class;

public interface ClassService extends BaseDataService<Class> {

  Class findByName(String name);

  Class findOne(Long id);
}
