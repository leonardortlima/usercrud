package com.leonardortlima.model;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "`user`")
public class User extends Model {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column
  private String email;

  public User() {
    super();
  }

  public User(OffsetDateTime createdAt, OffsetDateTime updatedAt, String firstName,
      String lastName, String email) {
    super(createdAt, updatedAt);
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
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

  public void mergeWith(User other) {
    if (other.firstName != null) {
      this.firstName = other.firstName;
    }

    if (other.lastName != null) {
      this.lastName = other.lastName;
    }

    if (other.email != null) {
      this.email = other.email;
    }
  }

  @Override public String toString() {
    return "User{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        "} " + super.toString();
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    User user = (User) o;

    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
      return false;
    }
    if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
    return email != null ? email.equals(user.email) : user.email == null;
  }

  @Override public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }
}
