package yerong.baedug.note.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.common.response.ApiResponseCustom;
import yerong.baedug.common.response.ResponseCode;
import yerong.baedug.note.domain.Note;
import yerong.baedug.note.dto.request.NoteSaveRequestDto;
import yerong.baedug.note.dto.request.UpdateNoteRequestDto;
import yerong.baedug.note.dto.response.NoteResponseDto;
import yerong.baedug.note.service.NoteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoteApiController {


    private final NoteService noteService;

    @PostMapping("/api/directory/{directoryId}/note")
    public ApiResponseCustom<?> addNote(@PathVariable Long directoryId, @RequestBody NoteSaveRequestDto requestDto){

        Note savedNote = noteService.save(requestDto, directoryId);
        return ApiResponseCustom.create(new NoteResponseDto(savedNote), ResponseCode.NOTE_CREATE_SUCCESS);
    }

    @GetMapping("/api/directory/{directoryId}/note")
    public ApiResponseCustom<?> findAllNote(@PathVariable Long directoryId){

        List<NoteResponseDto> noteResponseDtos = noteService.findAll(directoryId)
                .stream()
                .map(NoteResponseDto::new)
                .toList();

        return ApiResponseCustom.success(noteResponseDtos, ResponseCode.NOTE_READ_SUCCESS);
    }

    @GetMapping("/api/note/{noteId}")
    public ApiResponseCustom<?> findNote(@PathVariable Long noteId) {
        Note note = noteService.findById(noteId);
        return ApiResponseCustom.success(new NoteResponseDto(note), ResponseCode.NOTE_READ_SUCCESS);

    }

    @DeleteMapping("/api/note/{id}")
    public ApiResponseCustom<?> deleteNote(@PathVariable Long id){
        noteService.delete(id);
        return ApiResponseCustom.success(null, ResponseCode.NOTE_DELETE_SUCCESS);

    }
    @PutMapping("/api/note/{id}")
    public ApiResponseCustom<?> updateNote(@PathVariable Long id, @RequestBody UpdateNoteRequestDto dto) {

        Note update = noteService.update(id, dto);
        return ApiResponseCustom.success(new NoteResponseDto(update), ResponseCode.NOTE_UPDATE_SUCCESS);
    }
}
