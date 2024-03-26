package yerong.baedug.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.note.domain.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByDirectoryId(Long directoryId);
}
