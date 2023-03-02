package simple.mind.bearer.db.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    public User save(UserDto request) {
        var user = User.builder() //
                .firstname(request.getFirstname()) //
                .lastname(request.getLastname()) //
                .email(request.getEmail()) //
                .password(passwordEncoder.encode(request.getPassword())) //
                .role(Role.USER) //
                .build();
        return repository.save(user);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

}
