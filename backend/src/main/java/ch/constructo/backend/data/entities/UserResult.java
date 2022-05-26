package ch.constructo.backend.data.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_result")
public class UserResult extends AbstractEntity {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "attemptedGarment_id")
  private Garment garment;

  private Integer rightAmount;

  private LocalDateTime answerTime;

  private Boolean passed;

  public UserResult() {
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Garment getGarment() {
    return garment;
  }

  public void setGarment(Garment garment) {
    this.garment = garment;
  }

  public Integer getRightAmount() {
    return rightAmount;
  }

  public void setRightAmount(Integer rightAmount) {
    this.rightAmount = rightAmount;
  }

  public LocalDateTime getAnswerTime() {
    return answerTime;
  }

  public void setAnswerTime(LocalDateTime answerTime) {
    this.answerTime = answerTime;
  }

  public Boolean getPassed() {
    return passed;
  }

  public void setPassed(Boolean passed) {
    this.passed = passed;
  }
}
