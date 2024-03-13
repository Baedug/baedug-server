//package yerong.baedug.oauth;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import yerong.baedug.domain.member.Member;
//import yerong.baedug.repository.member.MemberRepository;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class OAuth2DetailsService extends DefaultOAuth2UserService {
//    private final MemberRepository memberRepository;
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        log.info("apple 로그인");
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        Map<String, Object> userInfo = oAuth2User.getAttributes();
//        String email = (String) userInfo.get("email");
//
//        Optional<Member> findUserOptional = memberRepository.findByEmail(email);
//
//        return new PrincipalDetails(findUserOptional.orElse(null), userInfo);
//    }
//}
