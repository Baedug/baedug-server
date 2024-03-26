package yerong.baedug.directory.service;

import yerong.baedug.directory.domain.Directory;
import yerong.baedug.directory.dto.request.DirectorySaveRequestDto;
import yerong.baedug.directory.dto.request.UpdateDirectoryRequestDto;

import java.util.List;

public interface DirectoryService {
    Directory save(DirectorySaveRequestDto requestDto, String socialId);
    Directory update(Long id, UpdateDirectoryRequestDto requestDto);
    void delete(Long id);
    Directory findById(Long id);
    List<Directory> findAll(String socialId);
}
