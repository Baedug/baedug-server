package yerong.baedug.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class AppleController {

    private final AppleService appleService;
    private final MemberService memberService;

    @PostMapping("/oauth2/code/apple")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws Exception {
        try {

            AppleDto appleInfo = appleService.getAppleInfo(code);
            // 신규 회원 저장

            MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                    .email(appleInfo.getEmail())
                    .socialId(appleInfo.getId())
                    .build();
            memberService.saveMember(memberRequestDto);

            return ResponseEntity.ok(new CustomEntity("Success", appleInfo));

        }catch (Exception e){
            log.error("Apple 소셜 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "Apple 소셜 로그인 오류"));
        }

    }
    @PostMapping("/token")
    public ResponseEntity<?> loginWithToken(@RequestHeader("Authorization") String token) {
        try {
            // 토큰을 검증하고 로그인 처리 수행
            // 필요한 작업을 수행한 후에 로그인된 사용자 정보를 반환하거나 성공 메시지를 반환합니다.
            return ResponseEntity.ok(new CustomEntity("Success", "User logged in successfully"));
        } catch (Exception e) {
            log.error("Login with token error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "Login with token error"));
        }
    }

}
