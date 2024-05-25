package yerong.baedug.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.common.exception.UserException;
import yerong.baedug.common.response.ResponseCode;
import yerong.baedug.member.domain.Member;
import yerong.baedug.member.domain.Role;
import yerong.baedug.member.domain.SocialProvider;
import yerong.baedug.member.dto.request.MemberRequestDto;
import yerong.baedug.oauth.domain.RefreshToken;
import yerong.baedug.oauth.jwt.JwtProvider;
import yerong.baedug.oauth.dto.TokenDto;
import yerong.baedug.oauth.repository.RefreshTokenRepository;
import yerong.baedug.member.repository.MemberRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto){
        Member member = memberRepository.findBySocialId(memberRequestDto.getSocialId()).orElse(null);

        if(member == null){
            member = Member.builder()
                    .socialId(memberRequestDto.getSocialId())
                    .email(memberRequestDto.getEmail())
                    .socialProvider(SocialProvider.APPLE)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        }

        TokenDto tokenDto = jwtProvider.generateTokenDto(memberRequestDto.getSocialId());

        RefreshToken refreshToken =
                RefreshToken.builder()
                .memberId(member.getId())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }
    @Transactional
    public TokenDto refreshAccessToken(String refreshTokenValue){
        // RefreshToken을 이용하여 Member 조회
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue).orElse(null);
        if (refreshToken == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Member member = memberRepository.findById(refreshToken.getMemberId())
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

        // 새로운 AccessToken 발급
        TokenDto tokenDto = jwtProvider.generateTokenDto(member.getSocialId());

        RefreshToken newRefreshToken = new RefreshToken(member.getId(), tokenDto.getRefreshToken());
        // 기존 RefreshToken 업데이트
        refreshToken.update(tokenDto.getRefreshToken());
        refreshTokenRepository.deleteByMemberId(member.getId());
        refreshTokenRepository.save(newRefreshToken);

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
