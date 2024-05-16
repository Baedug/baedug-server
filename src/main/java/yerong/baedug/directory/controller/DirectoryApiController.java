package yerong.baedug.directory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public class DirectoryApiController {

    private final DirectoryService directoryService;

    @Operation(summary = "디렉토리 생성", description = "디렉토리를 생성한다")
    @PostMapping("/api/directory")
    public ResponseEntity<DirectoryResponseDto> addDirectory(Principal principal, @RequestBody DirectorySaveRequestDto requestDto){
        String socialId = principal.getName();
        Directory savedDirectory = directoryService.save(requestDto, socialId);
        DirectoryResponseDto directoryResponseDto = new DirectoryResponseDto(savedDirectory);
            return ResponseEntity.status(HttpStatus.CREATED).body(directoryResponseDto);

    }

    @Operation(summary = "디렉토리 전체 조회", description = "디렉토리를 전체 조회한다")
    @GetMapping("/api/directory")
    public ResponseEntity<?> findAllDirectory(Principal principal){
        String socialId = principal.getName();
        List<DirectoryResponseDto> directories = directoryService.findAll(socialId)
                .stream()
                .map(DirectoryResponseDto::new)
                .toList();

        return ResponseEntity.ok().body(directories);
    }

    @Operation(summary = "디렉토리 id로 조회", description = "디렉토리를 디렉토리 id로 조회한다")
    @GetMapping("/api/directory/{id}")
    public ResponseEntity<?> findDirectory(@PathVariable Long id) {
        Directory directory = directoryService.findById(id);
        return ResponseEntity.ok().body(new DirectoryResponseDto(directory));
    }

    @Operation(summary = "디렉토리 삭제", description = "디렉토리를 id로 삭제한다")
    @DeleteMapping("/api/directory/{id}")
    public ResponseEntity<?> deleteDirectory(@PathVariable Long id){
        directoryService.delete(id);
        return ResponseEntity.ok().build();

    }
    @Operation(summary = "디렉토리 수정", description = "디렉토리를 id로 수정한다")
    @PutMapping("/api/directory/{id}")
    public ResponseEntity<?> updateDirectory(@PathVariable Long id, @RequestBody UpdateDirectoryRequestDto dto) {

        Directory updatedDirectory = directoryService.update(id, dto);
        return ResponseEntity.ok().body(new DirectoryResponseDto(updatedDirectory));
    }
}
