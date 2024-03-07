package yerong.baedug.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.dto.response.MemberResponseDto;
import yerong.baedug.service.MemberService;
import yerong.baedug.service.impl.MemberServiceImpl;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {

    private final AppleService appleService;
    private final MemberService memberService;

    @PostMapping("/login/oauth2/code/apple")
    public ResponseEntity<?> callback(HttpServletRequest request) throws Exception {
        try {
            log.info("=====Success1=====");

            AppleDto appleInfo = appleService.getAppleInfo(request.getParameter("code"), request.getParameter("user"));
            log.info(request.getParameter("user"));
            // 신규 회원 저장
            log.info("=====Success2=====");

            MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                    .email(appleInfo.getEmail())
                    .socialId(appleInfo.getId())
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
}
