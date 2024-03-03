package yerong.baedug.repository.directory;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.directory.Directory;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {
}
