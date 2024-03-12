package yerong.baedug.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.member.Member;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.repository.directory.DirectoryRepository;
import yerong.baedug.repository.member.MemberRepository;
import yerong.baedug.service.DirectoryService;
import yerong.baedug.service.MemberService;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;
    @Transactional
    @Override
    public Directory save(DirectorySaveRequestDto requestDto, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 id 입니다."));
        Directory directory = Directory.builder()
                .name(requestDto.getName())
                .member(member)
                .build();
        return directoryRepository.save(directory);
    }
}
