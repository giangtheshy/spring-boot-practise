package com.practise.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  private Boolean enabled;

  @JsonIgnore
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_roles",
      joinColumns = {@JoinColumn(name = "user_id",updatable = false,insertable = false)},
      inverseJoinColumns = {@JoinColumn(name = "role_id",updatable = false,insertable = false)})
  private Set<Role> roles;
}
