package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.Garment;
import ch.constructo.backend.data.entities.User;
import ch.constructo.backend.data.entities.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserResultRepository extends JpaRepository<UserResult, Long> {

  /**
   * <p>Finds One UserResult by Garemnt</p>
   *
   * @param garment a Garment (Object)
   * @return a UserResult
   */
  @Query("select c from UserResult c where c.garment = :garment")
  UserResult findByGarment(@Param("garment")Garment garment);

  /**
   * <p> Finds One UserResult by passed Condition </p>
   *
   * @param passed a {@link java.lang.Boolean} object.
   * @return a UserResult
   */
  @Query("select c from UserResult c where c.passed = :passed")
  UserResult findByPassed(@Param("passed") boolean passed);

  /**
   * <p>Find One UserResult by id</p>
   *
   * @param id a {@link java.lang.Long} object.
   * @return a UserResult
   */
  @Query("select a from UserResult a WHERE a.id = :id")
  UserResult findOne(@Param("id") Long id);

  @Query("select a from UserResult a WHERE a.user = :user")
  UserResult findByUser(@Param("user")User user);
}
