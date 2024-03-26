package yerong.baedug.heart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.note.dto.response.NoteResponseDto;
import yerong.baedug.heart.service.HeartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HeartApiController {

    private final HeartService heartService;

    @PostMapping("/api/note/{noteId}/heart")
    public ResponseEntity<?> heart(
            @PathVariable Long noteId,
            Principal principal
    ){
        String socialId = principal.getName();

        heartService.heart(noteId, socialId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/note/{noteId}/heart")
    public ResponseEntity<?> unHeart(
            @PathVariable Long noteId,
            Principal principal
    )
    {
        String socialId = principal.getName();

        heartService.unHeart(noteId, socialId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/heart")
    public ResponseEntity<?> findAllHeart(Principal principal){
        List<NoteResponseDto> noteResponseDtos = heartService.findAllHeart(principal.getName())
                .stream()
                .map(NoteResponseDto::new)
                .toList();

        return ResponseEntity.ok().body(noteResponseDtos);
    }
}
