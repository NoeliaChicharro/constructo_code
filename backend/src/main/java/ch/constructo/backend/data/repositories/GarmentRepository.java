package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.enums.GarmentType;
import ch.constructo.backend.data.entities.Garment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

  /**
   * <p>Finds One Garment by Name</p>
   *
   * @param name a {@link java.lang.String} object.
   * @return a Garemnt
   */
  @Query("select u from Garment u where u.name = :name")
  Garment findByName(@Param("name") String name);

  /**
   * <p>Finds One Garment by GarmentType</p>
   *
   * @param garmentType a Garmenttype
   * @return a Garment
   */
  @Query("select c from Garment c where c.garmentType = :garmentType")
  Garment findByGarmentType(@Param("garmentType") GarmentType garmentType);

  /**
   * <p>Finds One Garment by id</p>
   *
   * @param id a {@link java.lang.Long} object
   * @return a Garment
   */
  @Query("select a from Garment a WHERE a.id = :id")
  Garment findOne(@Param("id") Long id);

}
