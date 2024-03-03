package yerong.baedug.repository.note;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.baedug.domain.note.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
