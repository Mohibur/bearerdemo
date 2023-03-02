package simple.mind.bearer.ctrl.auth;

import lombok.RequiredArgsConstructor;
import simple.mind.bearer.db.user.UserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthCtrl {

  private final AuthService service;

  @PostMapping("/register")
  public ResponseEntity<AuthResDto> register(
      @RequestBody UserDto request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthResDto> authenticate(
      @RequestBody AuthReqDto request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
