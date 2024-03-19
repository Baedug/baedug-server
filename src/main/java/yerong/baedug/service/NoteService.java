package yerong.baedug.service;

import yerong.baedug.domain.note.Note;
import yerong.baedug.dto.request.note.NoteSaveRequestDto;
import yerong.baedug.dto.request.note.UpdateNoteRequestDto;

import java.util.List;

public interface NoteService {

    Note save(NoteSaveRequestDto requestDto, Long directoryId);
    List<Note> findAll(Long directoryId);
    Note findById(Long noteId);
    void delete(Long noteId);
    Note update(Long id, UpdateNoteRequestDto requestDto);
}
