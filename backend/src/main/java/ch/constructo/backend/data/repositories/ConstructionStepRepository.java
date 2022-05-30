package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.ConstructionStep;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConstructionStepRepository extends CrudRepository<ConstructionStep, Long> {

  @Query("select u from ConstructionStep u where u.text = :text")
  ConstructionStep findByText(@Param("text") String text);

  @Query("select a from ConstructionStep a WHERE a.id = :id")
  ConstructionStep findOne(@Param("id") Long id);
}
