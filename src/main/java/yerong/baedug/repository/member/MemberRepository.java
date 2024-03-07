package yerong.baedug.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);
    Optional<Member> findByEmail(String email);
}
