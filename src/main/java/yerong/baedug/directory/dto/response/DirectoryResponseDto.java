package yerong.baedug.directory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.baedug.directory.domain.Directory;

@Getter
@AllArgsConstructor
public class DirectoryResponseDto {

    private String name;

    public DirectoryResponseDto(Directory directory){
        this.name = directory.getName();
    }
}
