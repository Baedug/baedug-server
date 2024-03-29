package yerong.baedug.oauth.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yerong.baedug.oauth.service.apple.AppleService;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final AppleService appleService;

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("appleUrl", appleService.getAppleLogin());

        return "index";
    }
}