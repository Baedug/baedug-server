package yerong.baedug.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.member.Member;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.dto.request.directory.UpdateDirectoryRequestDto;
import yerong.baedug.repository.directory.DirectoryRepository;
import yerong.baedug.repository.member.MemberRepository;
import yerong.baedug.service.DirectoryService;
import yerong.baedug.service.MemberService;

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
        return directoryRepository.save(directory);
    }

    @Override
    @Transactional
    public List<Directory> findAll(){
        return directoryRepository.findAll();
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
        if(!directory.getMember().getSocialId().equals(directory)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
