package yerong.baedug.service;

import yerong.baedug.domain.directory.Directory;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;

public interface DirectoryService {
    Directory save(DirectorySaveRequestDto requestDto, Long memberId);
}
