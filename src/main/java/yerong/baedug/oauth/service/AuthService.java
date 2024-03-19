package yerong.baedug.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.domain.member.Member;
import yerong.baedug.domain.member.Role;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.oauth.domain.RefreshToken;
import yerong.baedug.oauth.jwt.JwtProvider;
import yerong.baedug.oauth.jwt.TokenDto;
import yerong.baedug.oauth.repository.RefreshTokenRepository;
import yerong.baedug.repository.member.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto){
        Member member = memberRepository.findBySocialId(memberRequestDto.getSocialId()).orElseThrow(null);
        if(member == null){
            Member savedMember = Member.builder()
                    .socialId(memberRequestDto.getSocialId())
                    .email(memberRequestDto.getEmail())
                    .role(Role.USER)
                    .build();
            memberRepository.save(savedMember);
        }
        log.info("[login] 계정을 찾았습니다. " + member);

        TokenDto tokenDto = jwtProvider.generateTokenDto(memberRequestDto.getSocialId());

        RefreshToken refreshToken =
                RefreshToken.builder()
                .memberId(member.getId())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }
    public HttpHeaders setTokenHeaders(TokenDto tokenDto) {
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", tokenDto.getRefreshToken())
                .path("/")
                .maxAge(60*60*24*7) // 쿠키 유효기간 7일로 설정
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        headers.add("Set-cookie", cookie.toString());
        headers.add("Authorization", tokenDto.getAccessToken());

        return headers;
    }
}
