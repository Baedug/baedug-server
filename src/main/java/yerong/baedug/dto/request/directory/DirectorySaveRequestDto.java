package yerong.baedug.dto.request.directory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.baedug.domain.directory.Directory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DirectorySaveRequestDto {

    private String name;
}
