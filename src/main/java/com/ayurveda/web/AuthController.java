package com.ayurveda.web;

import com.ayurveda.model.*; import com.ayurveda.repo.UserRepository; import com.ayurveda.security.JwtService;
import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*; import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody ApiDtos.RegisterRequest r){
  if(users.existsByEmail(r.email().toLowerCase()))return ResponseEntity.badRequest().body(Map.of("message","Email already registered"));
  AppUser u=users.save(new AppUser(r.name().trim(),r.email().toLowerCase(),encoder.encode(r.password()),Role.CUSTOMER));
  return ResponseEntity.status(201).body(response(u));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody ApiDtos.LoginRequest r){
  var u=users.findByEmail(r.email().toLowerCase()).orElse(null);
  if(u==null||!encoder.matches(r.password(),u.getPassword()))return ResponseEntity.status(401).body(Map.of("message","Invalid email or password"));
  return ResponseEntity.ok(response(u));
 }
 private ApiDtos.AuthResponse response(AppUser u){return new ApiDtos.AuthResponse(jwt.generate(u.getEmail(),u.getRole().name()),u.getName(),u.getEmail(),u.getRole().name());}
}
