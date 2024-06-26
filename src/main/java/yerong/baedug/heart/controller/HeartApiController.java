package yerong.baedug.heart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.common.response.ApiResponseCustom;
import yerong.baedug.common.response.ResponseCode;
import yerong.baedug.note.dto.response.NoteResponseDto;
import yerong.baedug.heart.service.HeartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HeartApiController {

    private final HeartService heartService;

    @PostMapping("/api/note/{noteId}/heart")
    public ApiResponseCustom<?> heart(
            @PathVariable Long noteId,
            Principal principal
    ){
        String socialId = principal.getName();
        heartService.heart(noteId, socialId);
        return ApiResponseCustom.create(null, ResponseCode.HEART_CREATE_SUCCESS);
    }

    @DeleteMapping("/api/note/{noteId}/heart")
    public ApiResponseCustom<?> unHeart(
            @PathVariable Long noteId,
            Principal principal
    )
    {
        String socialId = principal.getName();

        heartService.unHeart(noteId, socialId);
        return ApiResponseCustom.success(null, ResponseCode.HEART_DELETE_SUCCESS);
    }

    @GetMapping("/api/heart")
    public ApiResponseCustom<?> findAllHeart(Principal principal){
        List<NoteResponseDto> noteResponseDtos = heartService.findAllHeart(principal.getName())
                .stream()
                .map(NoteResponseDto::new)
                .toList();

        return ApiResponseCustom.success(noteResponseDtos, ResponseCode.HEART_READ_SUCCESS);
    }
}
