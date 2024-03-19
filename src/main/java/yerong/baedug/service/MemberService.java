package yerong.baedug.service;

import yerong.baedug.dto.request.member.MemberRequestDto;
import yerong.baedug.dto.response.member.MemberResponseDto;

public interface MemberService {
    MemberResponseDto findByAppleId(String appleId);
    MemberResponseDto saveMember(MemberRequestDto memberRequsetDto);
}
