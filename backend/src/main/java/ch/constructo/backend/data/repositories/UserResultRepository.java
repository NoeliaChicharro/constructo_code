package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserResultRepository extends JpaRepository<UserResult, Long> {

  @Query("select c from UserResult c where c.garment = :garment")
  UserResult findByGarment(@Param("garment")Garment garment);

  @Query("select c from UserResult c where c.passed = :passed")
  UserResult findByPassed(@Param("passed") boolean passed);

  @Query("select a from UserResult a WHERE a.id = :id")
  UserResult findOne(@Param("id") Long id);
}
