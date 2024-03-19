package yerong.baedug.oauth.apple;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.oauth.CustomEntity;
import yerong.baedug.oauth.jwt.TokenDto;
import yerong.baedug.oauth.service.AuthService;
import yerong.baedug.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {

    private final AppleService appleService;
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/login/oauth2/code/apple")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws Exception {
        try {
            log.info("success1");

            AppleDto appleInfo = appleService.getAppleInfo(code);

            log.info("success2");
            MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                    .email(appleInfo.getEmail())
                    .socialId(appleInfo.getId())
                    .build();
            log.info("success2 ==> memberDto = " + "socialId : " + memberRequestDto.getSocialId() +  "email : "  + memberRequestDto.getEmail());
            TokenDto tokenDto = authService.login(memberRequestDto);
            log.info("success3");

            HttpHeaders headers = authService.setTokenHeaders(tokenDto);
            log.info("success4");

            return ResponseEntity.ok().headers(headers).body(new CustomEntity("Success", "accessToken: " + tokenDto.getAccessToken()));

        }catch (Exception e){
            log.error("Apple 소셜 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "Apple 소셜 로그인 오류"));
        }

    }
}
