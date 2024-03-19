package yerong.baedug.controller.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.note.Note;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.dto.request.directory.UpdateDirectoryRequestDto;
import yerong.baedug.dto.request.note.NoteSaveRequestDto;
import yerong.baedug.dto.request.note.UpdateNoteRequestDto;
import yerong.baedug.dto.response.directory.DirectoryResponseDto;
import yerong.baedug.dto.response.note.NoteResponseDto;
import yerong.baedug.service.DirectoryService;
import yerong.baedug.service.NoteService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoteApiController {


    private final NoteService noteService;

    @PostMapping("/api/directory/{directoryId}/note")
    public ResponseEntity<?> addNote(@PathVariable Long directoryId, @RequestBody NoteSaveRequestDto requestDto){

        Note savedNote = noteService.save(requestDto, directoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);

    }

    @GetMapping("/api/directory/{directoryId}/note")
    public ResponseEntity<?> findAllNote(@PathVariable Long directoryId){

        List<NoteResponseDto> noteResponseDtos = noteService.findAll(directoryId)
                .stream()
                .map(NoteResponseDto::new)
                .toList();

        return ResponseEntity.ok().body(noteResponseDtos);
    }

    @GetMapping("/api/note/{noteId}")
    public ResponseEntity<?> findNote(@PathVariable Long noteId) {
        Note note = noteService.findById(noteId);
        return ResponseEntity.ok().body(new NoteResponseDto(note));
    }

    @DeleteMapping("/api/note/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id){
        noteService.delete(id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/api/note/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody UpdateNoteRequestDto dto) {

        Note update = noteService.update(id, dto);
        return ResponseEntity.ok().body(new NoteResponseDto(update));
    }
}
