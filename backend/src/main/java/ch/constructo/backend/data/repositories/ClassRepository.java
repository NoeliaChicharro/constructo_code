package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.Class;
import ch.constructo.backend.data.entities.Garment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClassRepository extends CrudRepository<Class, Long> {

  @Query("select u from Class u where u.name = :name")
  Class findByName(@Param("name") String name);

  @Query("select a from Class a WHERE a.id = :id")
  Class findOne(@Param("id") Long id);
}
