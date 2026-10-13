package com.ayurveda.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){
  if(secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalArgumentException("app.jwt.secret must be at least 32 bytes");
  this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expiration=expiration;
 }
 public String generate(String email,String role){long now=System.currentTimeMillis();return Jwts.builder().subject(email).claim("role",role).issuedAt(new Date(now)).expiration(new Date(now+expiration)).signWith(key).compact();}
 public String email(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
