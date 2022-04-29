package ch.constructo.backend.repositories;

import ch.constructo.backend.entities.ConstructionStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstructionStepRepository extends JpaRepository<ConstructionStep, Long> {

}
