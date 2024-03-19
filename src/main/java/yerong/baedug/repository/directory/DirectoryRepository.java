package yerong.baedug.repository.directory;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.directory.Directory;

import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    List<Directory> findAllByMemberId(Long id);
}
