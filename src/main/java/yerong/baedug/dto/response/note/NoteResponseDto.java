package yerong.baedug.dto.response.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.note.Note;

@Getter
@AllArgsConstructor
public class NoteResponseDto {

    private String title;
    private String content;

    public NoteResponseDto(Note note){
        this.title = note.getTitle();
        this.content = note.getContent();
    }
}
