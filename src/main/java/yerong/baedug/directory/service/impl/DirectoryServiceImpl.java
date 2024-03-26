package yerong.baedug.directory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.directory.domain.Directory;
import yerong.baedug.member.domain.Member;
import yerong.baedug.directory.dto.request.DirectorySaveRequestDto;
import yerong.baedug.directory.dto.request.UpdateDirectoryRequestDto;
import yerong.baedug.directory.repository.DirectoryRepository;
import yerong.baedug.member.repository.MemberRepository;
import yerong.baedug.directory.service.DirectoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final MemberRepository memberRepository;
    @Transactional
    @Override
    public Directory save(DirectorySaveRequestDto requestDto, String socialId){
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 id 입니다."));
        Directory directory = Directory.builder()
                .name(requestDto.getName())
                .member(member)
                .build();
        member.addDirectory(directory);

        return directoryRepository.save(directory);
    }

    @Override
    @Transactional
    public List<Directory> findAll(String socialId){
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 회원 id 입니다."));
        return directoryRepository.findAllByMemberId(member.getId());
    }

    @Override
    @Transactional
    public Directory findById(Long id){
        return directoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 디렉토리입니다."));
    }

    @Override
    @Transactional
    public void delete(Long id){
        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 디렉토리입니다."));
        authorizeDirectoryMember(directory);
        directory.removeMember();
        directoryRepository.delete(directory);
    }

    @Override
    @Transactional
    public Directory update(Long id, UpdateDirectoryRequestDto requestDto){
        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 디렉토리입니다."));
        authorizeDirectoryMember(directory);
        directory.update(requestDto.getName());
        return directory;
    }

    private static void authorizeDirectoryMember(Directory directory) {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!directory.getMember().getSocialId().equals(socialId)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
