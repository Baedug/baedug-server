package yerong.baedug.directory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.directory.domain.Directory;

import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    List<Directory> findAllByMemberId(Long id);
}
