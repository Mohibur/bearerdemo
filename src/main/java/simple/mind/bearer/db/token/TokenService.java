package simple.mind.bearer.db.token;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import simple.mind.bearer.db.user.User;

@Service
@AllArgsConstructor
public class TokenService {
  private TokenRepository repository;

  public Token save(User user, String jwtToken) {
    var token = Token.builder() //
        .user(user) //
        .token(jwtToken) //
        .tokenType(TokenType.BEARER) //
        .expired(false) //
        .revoked(false) //
        .build();
    return repository.save(token);
  }

  public void revoke(User user) {
    var validUserTokens = repository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    repository.saveAll(validUserTokens);
  }

  public void revoke(String jwt) {
    if (jwt.startsWith("Bearer ")) {
      jwt = jwt.replaceAll("^Bearer ", "");
    }
    var token = repository.findByToken(jwt).orElse(null);
    if (token != null) {
      token.setExpired(true);
      token.setRevoked(true);
      repository.save(token);
    }
  }

  public Token findByToken(String token) {
    return repository.findByToken(token).orElse(null);
  }

  public Boolean isTokenValid(String token) {
    token = token.replaceAll("^Bearer ", "");
    return repository.findByToken(token).map(t -> t.isExpired() == false && t.isRevoked() == false).orElse(false);
  }
}
