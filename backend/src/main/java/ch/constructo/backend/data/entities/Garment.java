package ch.constructo.backend.data.entities;


import ch.constructo.backend.data.enums.GarmentType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "garment")
public class Garment extends AbstractEntity {

  @NotNull
  @NotEmpty
  @Size(max = 200)
  private String name;

  @Enumerated(EnumType.STRING)
  private GarmentType garmentType;

  @Size(max = 1000)
  private String description;

  public Garment() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GarmentType getGarmentCategory() {
    return garmentType;
  }

  public void setGarmentCategory(GarmentType garmentType) {
    this.garmentType = garmentType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
