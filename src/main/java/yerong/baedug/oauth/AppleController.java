package yerong.baedug.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.dto.request.CodeDto;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.dto.response.MemberResponseDto;
import yerong.baedug.service.MemberService;
import yerong.baedug.service.impl.MemberServiceImpl;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class AppleController {

    private final AppleService appleService;
    private final MemberService memberService;

    @PostMapping("/oauth2/code/apple")
    public ResponseEntity<?> callback(@RequestParam String code, HttpServletRequest request) throws Exception {
        try {
            log.info("=====Success1=====");

            log.info(code);
            AppleDto appleInfo = appleService.getAppleInfo(code);
            log.info(request.getParameter("authorizationCode"));
            // 신규 회원 저장
            log.info("=====Success2=====");

            MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                    .email(appleInfo.getEmail())
                    .socialId(appleInfo.getId())
             //       .username(appleInfo.getUsername())
                    .build();
            memberService.saveMember(memberRequestDto);


            log.info("=====Success3=====");

            HttpSession session = request.getSession(true);
            session.setAttribute("userEmail", appleInfo.getEmail());
           // session.setAttribute("userName", appleInfo.getUsername());
            log.info("=====Success4=====");

            return ResponseEntity.ok(new MsgEntity("Success", appleInfo));

        }catch (Exception e){
            log.error("Apple 소셜 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MsgEntity("Error", "Apple 소셜 로그인 오류"));
        }

    }
    @PostMapping("/token")
    public ResponseEntity<?> loginWithToken(@RequestHeader("Authorization") String token) {
        try {
            // 토큰을 검증하고 로그인 처리 수행
            // 필요한 작업을 수행한 후에 로그인된 사용자 정보를 반환하거나 성공 메시지를 반환합니다.
            return ResponseEntity.ok(new MsgEntity("Success", "User logged in successfully"));
        } catch (Exception e) {
            log.error("Login with token error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MsgEntity("Error", "Login with token error"));
        }
    }

}
