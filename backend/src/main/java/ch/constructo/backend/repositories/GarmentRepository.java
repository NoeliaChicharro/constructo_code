package ch.constructo.backend.repositories;

import ch.constructo.backend.entities.Garment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

  // @TODO: Add findByName, findByType, findByUserId
}
