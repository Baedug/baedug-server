package yerong.baedug.oauth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {

    private final AppleService appleService;

    @RequestMapping(value = "/login/oauth2/code/apple", produces = "application/json")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        AppleDto appleInfo = appleService.getAppleInfo(request.getParameter("code"));
        log.info(appleInfo.toString());
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", appleInfo));
    }
}
