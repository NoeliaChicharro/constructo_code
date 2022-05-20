package ch.constructo.master.data.repositories;

import ch.constructo.master.data.entities.Garment;
import ch.constructo.master.data.enums.GarmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

  @Query("select u from Garment u where u.name = :name")
  Garment findByName(@Param("name") String name);

  @Query("select c from Garment c where c.garmentType = :gamentType")
  Garment findByGarmentType(@Param("garmentType") GarmentType garmentType);
}
