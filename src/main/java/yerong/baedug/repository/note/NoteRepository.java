package yerong.baedug.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.note.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByDirectoryId(Long directoryId);
}
