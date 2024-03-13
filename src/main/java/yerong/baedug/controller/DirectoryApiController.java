package yerong.baedug.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.member.Member;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.oauth.PrincipalDetails;
import yerong.baedug.service.DirectoryService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DirectoryApiController {

    private final DirectoryService directoryService;

    @PostMapping("/api/directory")
    public ResponseEntity<?> addDirectory(@RequestBody DirectorySaveRequestDto dto,@AuthenticationPrincipal PrincipalDetails principalDetails){

        log.info("====" + principalDetails.getMember().getSocialId());
        Directory savedDirectory = directoryService.save(dto, principalDetails.getMember().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDirectory);
    }
}
