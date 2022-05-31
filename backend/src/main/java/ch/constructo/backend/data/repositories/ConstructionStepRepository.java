package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.ConstructionStep;
import ch.constructo.backend.data.entities.Garment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConstructionStepRepository extends CrudRepository<ConstructionStep, Long> {

  /**
   * <p>Finds One ConstructionStep by text</p>
   * @param text a {@link java.lang.String} object.
   * @return ConstructionStep
   */
  @Query("select u from ConstructionStep u where u.text = :text")
  ConstructionStep findByText(@Param("text") String text);

  /**
   * <p>Finds One ConstraionStep by id</p>
   * @param id a {@link java.lang.Long} object.
   * @return ConstructionStep
   */
  @Query("select a from ConstructionStep a WHERE a.id = :id")
  ConstructionStep findOne(@Param("id") Long id);

  @Query("select c from ConstructionStep c WHERE c.garment = :garment")
  ConstructionStep findByGarment(@Param("garment")Garment garment);
}
