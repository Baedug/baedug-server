package yerong.baedug.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.baedug.domain.heart.Heart;
import yerong.baedug.domain.member.Member;
import yerong.baedug.repository.heart.HeartRepository;
import yerong.baedug.repository.member.MemberRepository;
import yerong.baedug.service.HeartService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void heart(Long noteId, String socialId){
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 social id 입니다."));

        heartRepository.mHeart(noteId, member.getId());
    }

    @Transactional
    @Override
    public void unHeart(Long noteId, String socialId){
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 social id 입니다."));

        heartRepository.mUnHeart(noteId, member.getId());
    }

    @Transactional
    @Override
    public List<Heart> findAllHeart(String socialId){
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 social id 입니다."));

        List<Heart> heartList = heartRepository.findAllByMemberId(member.getId());

        return heartList;
    }
}
