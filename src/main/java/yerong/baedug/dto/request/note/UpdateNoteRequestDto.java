package yerong.baedug.dto.request.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNoteRequestDto {
    private String title;
    private String content;
}
