package ch.constructo.backend.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  public Long getId(){
    return id;
  }

  public boolean isPersistend(){
    return id != null;
  }

  @Override
  public boolean equals(Object obj){
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractEntity other = (AbstractEntity) obj;
    if (getId() == null || other.getId() == null) {
      return false;
    }
    return getId().equals(other.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
