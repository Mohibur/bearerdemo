package simple.mind.bearer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtProcessor {

  private static final String jwtSecretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
  private Claims claims;
  @Getter
  private String token;

  public JwtProcessor(String token) {
    this.token = token.replaceAll("Bearer ", "");
    this.extractAllClaims();
  }

  public JwtProcessor(Map<String, Object> extraClaims, //
      UserDetails userDetails) {
    this.generateToken(extraClaims, userDetails);
    this.extractAllClaims();
  }

  public JwtProcessor(UserDetails userDetails) {
    generateToken(new HashMap<>(), userDetails);
    this.extractAllClaims();
  }

  private void generateToken( //
      Map<String, Object> extraClaims, //
      UserDetails userDetails) {
    this.token = Jwts //
        .builder() //
        .setClaims(extraClaims) //
        .setSubject(userDetails.getUsername()) //
        .setIssuedAt(new Date(System.currentTimeMillis())) //
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //
        .signWith(getSignInKey(), SignatureAlgorithm.HS256) //
        .compact();
  }

  public String getUserEmail() {
    return claims.getSubject();
  }

  public boolean isTokenValid(final String username, UserDetails userDetails) {
    return (username.equals(userDetails.getUsername())) && !isTokenExpired();
  }

  private void extractAllClaims() {
    this.claims = Jwts //
        .parserBuilder() //
        .setSigningKey(getSignInKey()) //
        .build() //
        .parseClaimsJws(token) //
        .getBody();
  }

  private boolean isTokenExpired() {
    return claims.getExpiration().before(new Date());
  }

  private static Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
