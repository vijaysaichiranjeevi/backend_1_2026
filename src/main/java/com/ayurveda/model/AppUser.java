package com.ayurveda.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String name;
 @Column(nullable=false,unique=true,length=180) private String email;
 @Column(nullable=false) private String password;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.CUSTOMER;
 @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 public AppUser() {}
 public AppUser(String name,String email,String password,Role role){this.name=name;this.email=email;this.password=password;this.role=role;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 @JsonIgnore public String getPassword(){return password;} public void setPassword(String v){password=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
