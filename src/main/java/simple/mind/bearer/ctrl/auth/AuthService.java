package simple.mind.bearer.ctrl.auth;

import lombok.RequiredArgsConstructor;
import simple.mind.bearer.db.token.TokenService;
import simple.mind.bearer.db.user.UserDto;
import simple.mind.bearer.db.user.UserService;
import simple.mind.bearer.service.JwtProcessor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final TokenService tokenService;

  private final AuthenticationManager authenticationManager;

  public AuthResDto register(UserDto userDto) {
    var user = userService.save(userDto);
    var jwtToken = new JwtProcessor(user).getToken();
    tokenService.save(user, jwtToken);
    return AuthResDto.builder().token(jwtToken).build();
  }

  public AuthResDto authenticate(AuthReqDto request) {
    authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = userService.findByEmail(request.getEmail());
    var jwtToken = new JwtProcessor(user).getToken();
    tokenService.revoke(user);
    tokenService.save(user, jwtToken);
    return AuthResDto.builder().token(jwtToken).build();
  }
}
