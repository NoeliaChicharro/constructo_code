package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.enums.GarmentType;
import ch.constructo.backend.data.entities.Garment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

  @Query("select u from Garment u where u.name = :name")
  Garment findByName(@Param("name") String name);

  @Query("select c from Garment c where c.garmentType = :garmentType")
  Garment findByGarmentType(@Param("garmentType") GarmentType garmentType);
}
