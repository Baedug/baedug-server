package yerong.baedug.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import yerong.baedug.domain.member.Member;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.dto.response.MemberResponseDto;
import yerong.baedug.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {

    private final AppleService appleService;
    private final MemberService memberService;
    private final OAuth2DetailsService oAuth2DetailsService;

    @PostMapping("/login/oauth2/code/apple")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws Exception {
        try {
            AppleDto appleInfo = appleService.getAppleInfo(code);
//
//            MemberRequestDto memberRequestDto = MemberRequestDto.builder()
//                    .email(appleInfo.getEmail())
//                    .socialId(appleInfo.getId())
//                    .build();
//            memberService.saveMember(memberRequestDto);
            return ResponseEntity.ok(new CustomEntity("Success", appleInfo));

        }catch (Exception e){
            log.error("Apple 소셜 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "Apple 소셜 로그인 오류"));
        }

    }

    @PostMapping("/login/oauth2/token/apple")
    public ResponseEntity<?> loginWithToken(@RequestParam("token") String token, HttpServletRequest request) throws Exception {
        try {
            AppleDto appleDto = appleService.getAppleInfoByRefresh(token);



            return ResponseEntity.ok(new CustomEntity("Success", appleDto));
        }
        catch (Exception e) {
            // 에러 발생 시 에러 응답 반환
            log.error("Error occurred during login with token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomEntity("Error", "Login failed"));
        }
    }
}
