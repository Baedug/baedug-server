//package yerong.baedug.oauth;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import yerong.baedug.domain.member.Member;
//import yerong.baedug.domain.member.Role;
//import yerong.baedug.domain.member.SocialProvider;
//import yerong.baedug.repository.member.MemberRepository;
//
//import javax.swing.text.html.Option;
//import java.util.Map;
//import java.util.Optional;
//
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class OAuth2DetailsService extends DefaultOAuth2UserService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        log.info("apple 로그인");
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        Map<String, Object> userInfo = oAuth2User.getAttributes();
//        String email = (String) userInfo.get("email");
//        String socialId = (String) userInfo.get("socialId");
//
//        Optional<Member> findMemberOptional = memberRepository.findBySocialId(socialId);
//
//        if(!findMemberOptional.isPresent()){
//            Member member = Member.builder()
//                    .email(email)
//                    .socialProvider(SocialProvider.APPLE)
//                    .role(Role.USER)
//
//                    .socialId(socialId)
//                    .build();
//            return new PrincipalDetails(memberRepository.save(member), userInfo);
//        }
//        else{
//            return new PrincipalDetails(findMemberOptional.get(), userInfo);
//        }
//    }
//}
