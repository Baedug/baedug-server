package yerong.baedug.directory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DirectorySaveRequestDto {

    @NotBlank(message = "디렉토리 명을 입력해주세요.")
    @Length(max = 20, message = "디렉토리 명은 20글자 이하로 입력해야 합니다.")
    @Schema(description = "directory name", example = "과학")
    private String name;
}
