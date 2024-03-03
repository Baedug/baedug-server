package yerong.baedug.repository.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.heart.Heart;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
