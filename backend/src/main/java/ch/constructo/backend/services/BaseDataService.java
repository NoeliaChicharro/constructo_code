package ch.constructo.backend.services;

import ch.constructo.backend.data.entities.AbstractEntity;

import java.util.List;

public interface BaseDataService<T extends AbstractEntity> {

  /**
   * <p>save.</p>
   *
   * @param s a T object.
   * @return a T object.
   */
  T save(T s);

  /**
   * <p>delete.</p>
   *
   * @param aLong a {@link java.lang.Long} object.
   */
  void delete(Long aLong);

  /**
   * <p>exists.</p>
   *
   * @param aLong a {@link java.lang.Long} object.
   * @return a boolean.
   */
  boolean exists(Long aLong);

  /**
   * <p>findOne.</p>
   *
   * @param aLong a {@link java.lang.Long} object.
   * @return a T object.
   */
  T findOne(Long aLong);

  /**
   * <p>delete.</p>
   *
   * @param object a T object.
   */
  void delete(T object);

  /**
   * <p>count.</p>
   *
   * @return a long.
   */
  long count();

  /**
   * <p>deleteAll.</p>
   */
  void deleteAll();

  /**
   * <p>findAll.</p>
   *
   * @return a {@link java.util.List} object.
   */
  List<T> findAll();

  /**
   * <p>findAll.</p>
   *
   * @param iterable a {@link java.util.List} object.
   * @return a {@link java.util.List} object.
   */
  List<T> findAll(List<Long> iterable);

  /**
   * <p>delete.</p>
   *
   * @param iterable a {@link java.util.List} object.
   */
  void delete(List<T> iterable);

  /**
   * <p>save.</p>
   *
   * @param iterable a {@link List} object.
   * @return a {@link java.util.List} object.
   */
  List<T> save(Iterable<T> iterable);

}
