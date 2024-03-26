package yerong.baedug.note.service;

import yerong.baedug.note.domain.Note;
import yerong.baedug.note.dto.request.NoteSaveRequestDto;
import yerong.baedug.note.dto.request.UpdateNoteRequestDto;

import java.util.List;

public interface NoteService {

    Note save(NoteSaveRequestDto requestDto, Long directoryId);
    List<Note> findAll(Long directoryId);
    Note findById(Long noteId);
    void delete(Long noteId);
    Note update(Long id, UpdateNoteRequestDto requestDto);
}
