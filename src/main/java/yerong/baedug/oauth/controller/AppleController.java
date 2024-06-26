package yerong.baedug.oauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.member.dto.request.MemberRequestDto;
import yerong.baedug.oauth.common.CustomEntity;
import yerong.baedug.oauth.dto.AppleDto;
import yerong.baedug.oauth.dto.TokenDto;
import yerong.baedug.oauth.service.AuthService;
import yerong.baedug.member.service.MemberService;
import yerong.baedug.oauth.service.apple.AppleService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {

    private final AppleService appleService;
    private final AuthService authService;

    @PostMapping("/login/oauth2/code/apple")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws Exception {
        try {
            AppleDto appleInfo = appleService.getAppleInfo(code);

            MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                    .email(appleInfo.getEmail())
                    .socialId(appleInfo.getId())
                    .build();
            TokenDto tokenDto = authService.login(memberRequestDto);

            HttpHeaders headers = authService.setTokenHeaders(tokenDto);

            return ResponseEntity.ok().headers(headers).body(new CustomEntity("Success", "accessToken: " + tokenDto.getAccessToken()));

        }catch (Exception e){
            log.error("Apple 소셜 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "Apple 소셜 로그인 오류"));
        }

    }

    @PostMapping("/login/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("RefreshToken") String refreshToken) {
        try {
            // RefreshToken을 사용하여 새로운 AccessToken 및 RefreshToken 발급
            TokenDto tokenDto = authService.refreshAccessToken(refreshToken);
            HttpHeaders headers = authService.setTokenHeaders(tokenDto);

            return ResponseEntity.ok().headers(headers).body(new CustomEntity("Success", "accessToken: " + tokenDto.getAccessToken()));
        } catch (Exception e) {
            log.error("RefreshToken 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "RefreshToken 오류"));
        }
    }

}
