package yerong.baedug.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
