package ch.constructo.backend.repositories;

import ch.constructo.backend.entities.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResultRepository extends JpaRepository<UserResult, Long> {

  // @TODO: add findByGarment, findByPassed
}
