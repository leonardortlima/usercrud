package com.leonardortlima.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.leonardortlima.rest.representation.OffsetDateTimeSerializer;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  public Model() {
  }

  public Model(OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override public String toString() {
    return "Model{" +
        "id=" + id +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Model model = (Model) o;

    if (id != null ? !id.equals(model.id) : model.id != null) return false;
    if (createdAt != null ? !createdAt.equals(model.createdAt) : model.createdAt != null) {
      return false;
    }
    return updatedAt != null ? updatedAt.equals(model.updatedAt) : model.updatedAt == null;
  }

  @Override public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    return result;
  }
}
