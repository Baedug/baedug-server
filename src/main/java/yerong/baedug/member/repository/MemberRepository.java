package yerong.baedug.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);
    Optional<Member> findByEmail(String email);
}
