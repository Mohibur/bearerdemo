package simple.mind.bearer.ctrl.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication Response DTO
 * @author johny
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResDto {

  private String token;
}
