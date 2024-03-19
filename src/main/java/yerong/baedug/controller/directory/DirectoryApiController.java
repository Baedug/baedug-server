package yerong.baedug.controller.directory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.dto.request.directory.UpdateDirectoryRequestDto;
import yerong.baedug.dto.response.directory.DirectoryResponseDto;
import yerong.baedug.service.DirectoryService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DirectoryApiController {

    private final DirectoryService directoryService;

    @PostMapping("/api/directory")
    public ResponseEntity<?> addDirectory(Principal principal, @RequestBody DirectorySaveRequestDto requestDto){
        String socialId = principal.getName();
        Directory savedDirectory = directoryService.save(requestDto, socialId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectory);

    }

    @GetMapping("/api/directory")
    public ResponseEntity<?> findAllDirectory(Principal principal){
        String socialId = principal.getName();
        List<DirectoryResponseDto> directories = directoryService.findAll(socialId)
                .stream()
                .map(DirectoryResponseDto::new)
                .toList();

        return ResponseEntity.ok().body(directories);
    }

    @GetMapping("/api/directory/{id}")
    public ResponseEntity<?> findDirectory(@PathVariable Long id) {
        Directory directory = directoryService.findById(id);
        return ResponseEntity.ok().body(new DirectoryResponseDto(directory));
    }

    @DeleteMapping("/api/directory/{id}")
    public ResponseEntity<?> deleteDirectory(@PathVariable Long id){
        directoryService.delete(id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/api/directory/{id}")
    public ResponseEntity<?> updateDirectory(@PathVariable Long id, @RequestBody UpdateDirectoryRequestDto dto) {

        Directory updatedDirectory = directoryService.update(id, dto);
        return ResponseEntity.ok().body(new DirectoryResponseDto(updatedDirectory));
    }
}
