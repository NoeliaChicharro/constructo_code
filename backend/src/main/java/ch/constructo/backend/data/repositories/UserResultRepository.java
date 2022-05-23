package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserResultRepository extends JpaRepository<UserResult, Long> {

  // @TODO: add findByGarment, findByPassed
  @Query("select c from UserResult c where c.garment = :garment")
  UserResult findByGarment(@Param("garment")Garment garment);

  @Query("select c from UserResult c where c.passed = :passed")
  UserResult findByPassed(@Param("passed") boolean passed);

}
