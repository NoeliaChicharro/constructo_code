package ch.constructo.backend.entities;


import ch.constructo.backend.enums.StepType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "construction_step")
public class ConstructionStep extends AbstractEntity {

  @NotNull
  @NotEmpty
  @Size(max = 300)
  private String text;

  @ManyToOne
  @JoinColumn(name = "garment_id")
  private Garment garment;

  @Enumerated(EnumType.STRING)
  private StepType stepType;

  public ConstructionStep() {
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Garment getGarment() {
    return garment;
  }

  public void setGarment(Garment garment) {
    this.garment = garment;
  }

  public StepType getStepType() {
    return stepType;
  }

  public void setStepType(StepType stepType) {
    this.stepType = stepType;
  }
}
