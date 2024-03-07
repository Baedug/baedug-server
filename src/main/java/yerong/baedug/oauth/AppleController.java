package yerong.baedug.oauth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.service.MemberService;
import yerong.baedug.service.impl.MemberServiceImpl;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {

    private final AppleService appleService;
    private final MemberService memberService;

    @RequestMapping(value = "/login/oauth2/code/apple", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        AppleDto appleInfo = appleService.getAppleInfo(request.getParameter("code"));
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .email(appleInfo.getEmail())
                .socialId(appleInfo.getId())
                .username(appleInfo.getUsername())
                .build();
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", appleInfo));
    }
}
