package yerong.baedug.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.member.domain.Member;
import yerong.baedug.member.domain.Role;
import yerong.baedug.member.domain.SocialProvider;
import yerong.baedug.member.dto.request.MemberRequestDto;
import yerong.baedug.member.dto.response.MemberResponseDto;
import yerong.baedug.member.repository.MemberRepository;
import yerong.baedug.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto findByAppleId(String appleId){
        Member member = memberRepository.findBySocialId(appleId).orElseThrow();
        if(member !=null){
            return MemberResponseDto.builder()
                    .email(member.getEmail()).build();
        }
        else {
            return null;
        }
    }

    @Override
    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto memberRequestDto){
        Member findMember = memberRepository.findByEmail(memberRequestDto.getEmail()).orElse(null);
        if (findMember == null) {
            Member member = Member.builder()
                    .email(memberRequestDto.getEmail())
                    //.username(memberRequestDto.getUsername())
                    .role(Role.USER)
                    .socialProvider(SocialProvider.APPLE)
                    .socialId(memberRequestDto.getSocialId())
                    .build();
            memberRepository.save(member);
            return MemberResponseDto.builder()
                    .email(member.getEmail()).build();

        }
        else {
            return MemberResponseDto.builder()
                    .email(findMember.getEmail()).build();
        }

    }
}
