package ch.constructo.backend.data.entities;

import ch.constructo.backend.data.enums.Role;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "constructo_user")
public class User extends AbstractEntity {

  @Size(max = 100)
  private String firstName;

  @Size(max = 100)
  private String lastName;

  @Email
  @NotNull
  @NotEmpty
  private String email;

  @NotNull
  @NotEmpty
  @Size(max = 100)
  private String username;

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 40)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  public User() {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
