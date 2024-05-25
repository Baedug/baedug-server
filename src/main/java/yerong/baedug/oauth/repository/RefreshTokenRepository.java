package yerong.baedug.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.oauth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByMemberId(Long memberId);
}