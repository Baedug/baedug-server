package yerong.baedug.service;

import yerong.baedug.domain.directory.Directory;
import yerong.baedug.dto.request.directory.DirectorySaveRequestDto;
import yerong.baedug.dto.request.directory.UpdateDirectoryRequestDto;

import java.util.List;

public interface DirectoryService {
    Directory save(DirectorySaveRequestDto requestDto, String socialId);
    Directory update(Long id, UpdateDirectoryRequestDto requestDto);
    void delete(Long id);
    Directory findById(Long id);
    List<Directory> findAll();

}
