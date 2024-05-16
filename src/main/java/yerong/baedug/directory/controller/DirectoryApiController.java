package yerong.baedug.directory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.common.response.ApiResponseCustom;
import yerong.baedug.common.response.ResponseCode;
import yerong.baedug.directory.domain.Directory;
import yerong.baedug.directory.dto.request.DirectorySaveRequestDto;
import yerong.baedug.directory.dto.request.UpdateDirectoryRequestDto;
import yerong.baedug.directory.dto.response.DirectoryResponseDto;
import yerong.baedug.directory.service.DirectoryService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Directory", description = "Directory API")
public class DirectoryApiController {

    private final DirectoryService directoryService;

    @Operation(summary = "디렉토리 생성", description = "디렉토리를 생성하는 Controller")
    @PostMapping("/api/directory")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "디렉토리 생성 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DirectoryResponseDto.class)) )
    })
    public ApiResponseCustom<DirectoryResponseDto> addDirectory(Principal principal, @RequestBody DirectorySaveRequestDto requestDto){
        String socialId = principal.getName();
        Directory savedDirectory = directoryService.save(requestDto, socialId);
        DirectoryResponseDto directoryResponseDto = new DirectoryResponseDto(savedDirectory);
            return ApiResponseCustom.create(directoryResponseDto, ResponseCode.DIRECTORY_CREATE_SUCCESS);
    }

    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "디렉토리 전체 조회 성공", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "디렉토리 전체 조회", description = "디렉토리를 전체 조회하는 Controller")
    @GetMapping("/api/directory")
    public ApiResponseCustom<?> findAllDirectory(Principal principal){
        String socialId = principal.getName();
        List<DirectoryResponseDto> directories = directoryService.findAll(socialId)
                .stream()
                .map(DirectoryResponseDto::new)
                .toList();

        return ApiResponseCustom.success(directories, ResponseCode.DIRECTORY_READ_ALL_SUCCESS);
    }

    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "디렉토리 조회 성공", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "디렉토리 id로 조회", description = "디렉토리를 디렉토리 id로 조회하는 Controller")
    @GetMapping("/api/directory/{id}")
    public ApiResponseCustom<?> findDirectory(@PathVariable Long id) {
        Directory directory = directoryService.findById(id);
        return ApiResponseCustom.create(new DirectoryResponseDto(directory), ResponseCode.DIRECTORY_READ_BY_ID_SUCCESS);
    }

    @Operation(summary = "디렉토리 삭제", description = "디렉토리를 id로 삭제하는 Controller")
    @DeleteMapping("/api/directory/{id}")
    public ApiResponseCustom<?> deleteDirectory(@Parameter(required = true, description = "게시글 고유 번호") @PathVariable Long id){
        directoryService.delete(id);
        return ApiResponseCustom.success(ResponseCode.DIRECTORY_DELETE_SUCCESS);

    }
    @Operation(summary = "디렉토리 수정", description = "디렉토리를 id로 수정하는 Controller")
    @PutMapping("/api/directory/{id}")
    public ApiResponseCustom<?> updateDirectory(@PathVariable Long id, @RequestBody UpdateDirectoryRequestDto dto) {

        Directory updatedDirectory = directoryService.update(id, dto);
        return ApiResponseCustom.success(new DirectoryResponseDto(updatedDirectory), ResponseCode.DIRECTORY_UPDATE_SUCCESS);
    }
}
