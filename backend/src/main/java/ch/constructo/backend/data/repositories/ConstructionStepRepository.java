package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConstructionStepRepository extends CrudRepository<ConstructionStep, Long> {

  @Query("select u from ConstructionStep u where u.text = :text")
  ConstructionStep findByText(@Param("text") String text);
}
