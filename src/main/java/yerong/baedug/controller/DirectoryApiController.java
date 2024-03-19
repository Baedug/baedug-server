package yerong.baedug.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.service.DirectoryService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DirectoryApiController {

    private final DirectoryService directoryService;

    @PostMapping("/api/directory")
    public ResponseEntity<?> addDirectory(Principal principal, @RequestBody DirectorySaveRequestDto requestDto){
        String socialId = principal.getName();
        log.info("socialId ====== " + socialId);
        Directory savedDirectory = directoryService.save(requestDto, socialId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectory);

    }
}
