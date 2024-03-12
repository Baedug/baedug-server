package yerong.baedug.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.member.Member;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.service.DirectoryService;

@RestController
@RequiredArgsConstructor
public class DirectoryApiController {

    private final DirectoryService directoryService;

    @PostMapping("/api/directory")
    public ResponseEntity<?> addDirectory(@RequestBody DirectorySaveRequestDto dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member principal = (Member) authentication.getPrincipal();

        Directory savedDirectory = directoryService.save(dto, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectory);
    }
}
