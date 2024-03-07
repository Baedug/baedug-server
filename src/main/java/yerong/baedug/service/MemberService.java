package yerong.baedug.service;

import yerong.baedug.dto.request.MemberRequestDto;
import yerong.baedug.dto.response.MemberResponseDto;

public interface MemberService {
    MemberResponseDto findByAppleId(String appleId);
    void saveMember(MemberRequestDto memberRequsetDto);
}
