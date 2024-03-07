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
    public void saveMember(MemberRequestDto memberRequsetDto){
        Member member = Member.builder()
                        .email(memberRequsetDto.getEmail())
                                .username(memberRequsetDto.getUsername())
                                        .role(Role.USER)
                                                .socialProvider(SocialProvider.APPLE)
                                                        .build();
        memberRepository.save(member);
    }
}
