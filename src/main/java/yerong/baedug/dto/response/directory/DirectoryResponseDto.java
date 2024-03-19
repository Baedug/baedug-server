package yerong.baedug.dto.response.directory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.baedug.domain.directory.Directory;

@Getter
@AllArgsConstructor
public class DirectoryResponseDto {

    private String name;

    public DirectoryResponseDto(Directory directory){
        this.name = directory.getName();
    }
}
