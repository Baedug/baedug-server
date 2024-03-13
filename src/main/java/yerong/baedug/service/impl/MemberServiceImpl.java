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
        Member member = memberRepository.findBySocialId(appleId).orElseThrow();
        if(member !=null){
            return MemberResponseDto.builder()
                    .email(member.getEmail()).build();
        }
        else {
            return null;
        }
    }

//    @Override
//    @Transactional
//    public MemberResponseDto saveMember(MemberRequestDto memberRequestDto){
//        Member findMember = memberRepository.findByEmail(memberRequestDto.getEmail()).orElse(null);
//        if (findMember == null) {
//            Member member = Member.builder()
//                    .email(memberRequestDto.getEmail())
//                    //.username(memberRequestDto.getUsername())
//                    .role(Role.USER)
//                    .socialProvider(SocialProvider.APPLE)
//                    .socialId(memberRequestDto.getSocialId())
//                    .build();
//            memberRepository.save(member);
//            return MemberResponseDto.builder()
//                    .email(member.getEmail()).build();
//
//        }
//        else {
//            return MemberResponseDto.builder()
//                    .email(findMember.getEmail()).build();
//        }
//
//    }
}
