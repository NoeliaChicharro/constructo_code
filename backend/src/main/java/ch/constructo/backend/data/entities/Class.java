package ch.constructo.backend.data.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "class")
public class Class extends AbstractEntity{

  @Size(max = 100)
  private String name;

  @ManyToOne
  @JoinColumn(name = "constructo_user_id")
  private User mainTeacher;

  @OneToMany
  @JoinColumn(name = "user_id")
  private List<User> student;

  public Class() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getMainTeacher() {
    return mainTeacher;
  }

  public void setMainTeacher(User mainTeacher) {
    this.mainTeacher = mainTeacher;
  }

  public List<User> getStudent() {
    return student;
  }

  public void setStudent(List<User> student) {
    this.student = student;
  }
}
