package yerong.baedug.repository.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import yerong.baedug.domain.heart.Heart;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    @Modifying
    @Query(value = "INSERT INTO heart(note_id, member_id, created_at) VALUES (:noteId, :memberId, now())", nativeQuery = true)
    int mHeart(Long noteId, Long memberId);
    @Modifying
    @Query(value = "DELETE FROM heart WHERE note_id = :noteId AND member_id = :memberId", nativeQuery = true)
    int mUnHeart(Long noteId, Long memberId);

    List<Heart> findAllByMemberId(Long memberId);
}
