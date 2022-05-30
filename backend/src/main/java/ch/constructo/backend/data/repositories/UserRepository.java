package ch.constructo.backend.data.repositories;

import ch.constructo.backend.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * <p>Finds One User by username</p>
   *
   * @param username a {@link java.lang.String} object.
   * @return a User
   */
  @Query("select u from User u where u.username = :username")
  User findByUsername(@Param("username") String username);

  /**
   * <p>Finds One User by id</p>
   *
   * @param id a {@link java.lang.Long} object.
   * @return a User
   */
  @Query("select a from User a WHERE a.id = :id")
  User findOne(@Param("id") Long id);
}
