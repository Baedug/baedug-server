package yerong.baedug.directory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.baedug.directory.domain.Directory;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DirectoryResponseDto {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DirectoryResponseDto(Directory directory){
        this.id = directory.getId();
        this.name = directory.getName();
        this.createdAt = directory.getCreatedAt();
        this.updatedAt = directory.getUpdatedAt();
    }
}
