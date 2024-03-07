package yerong.baedug.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.domain.member.Member;
import yerong.baedug.domain.member.Role;
import yerong.baedug.domain.member.SocialProvider;
import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.dto.response.MemberResponseDto;
import yerong.baedug.repository.member.MemberRepository;
import yerong.baedug.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto findByAppleId(String appleId){
        Member member = memberRepository.findBySocialId(appleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 SocialId 입니다."));
        return MemberResponseDto.builder()
                .username(member.getUsername())
                .email(member.getEmail()).build();
    }

    @Override
    @Transactional
    public void saveMember(MemberRequestDto memberRequestDto){
        if (memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일 주소입니다.");
        }

        // 회원 정보 저장
        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .username(memberRequestDto.getUsername())
                .role(Role.USER)
                .socialProvider(SocialProvider.APPLE)
                .socialId(memberRequestDto.getSocialId())
                .build();
        memberRepository.save(member);
    }
}
