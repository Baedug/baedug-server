package yerong.baedug.heart.service;

import yerong.baedug.heart.domain.Heart;

import java.util.List;

public interface HeartService {
    void heart(Long noteId, String socialId);
    void unHeart(Long noteId, String socialId);
    List<Heart> findAllHeart(String socialId);
}
