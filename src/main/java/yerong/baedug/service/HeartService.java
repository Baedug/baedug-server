package yerong.baedug.service;

import yerong.baedug.domain.heart.Heart;

import java.util.List;

public interface HeartService {
    void heart(Long noteId, String socialId);
    void unHeart(Long noteId, String socialId);
    List<Heart> findAllHeart(String socialId);
}
