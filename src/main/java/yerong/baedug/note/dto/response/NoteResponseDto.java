package yerong.baedug.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.baedug.heart.domain.Heart;
import yerong.baedug.note.domain.Note;

@Getter
@AllArgsConstructor
public class NoteResponseDto {

    private String title;
    private String content;

    public NoteResponseDto(Note note){
        this.title = note.getTitle();
        this.content = note.getContent();
    }

    public NoteResponseDto(Heart heart){
        this.title = heart.getNote().getTitle();
        this.content = heart.getNote().getContent();
    }
}
