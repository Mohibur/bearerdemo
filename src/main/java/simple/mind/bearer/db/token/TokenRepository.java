package simple.mind.bearer.db.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            SELECT
                t.*
            FROM token t
            INNER JOIN _user u ON t.id = u.id
            WHERE
                  u.id = :id
              AND (t.expired = false OR t.revoked = false)
            """, nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
