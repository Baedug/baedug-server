package yerong.baedug.member.service;

import yerong.baedug.member.dto.request.MemberRequestDto;
import yerong.baedug.member.dto.response.MemberResponseDto;

public interface MemberService {
    MemberResponseDto findByAppleId(String appleId);
    MemberResponseDto saveMember(MemberRequestDto memberRequsetDto);
}
