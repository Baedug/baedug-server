package yerong.baedug.domain.note;

import jakarta.persistence.*;
import lombok.*;
import yerong.baedug.domain.BaseTimeEntity;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.heart.Heart;
import yerong.baedug.domain.member.Member;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Note extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "directory_id")
    private Directory directory;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "heart_id")
    private Heart heart;


    @Builder
    public Note(String title, String content, Directory directory){
        this.title = title;
        this.content = content;
        this.directory = directory;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void setDirectory(Directory directory){
        this.directory = directory;
    }
    public void removeDirectory() {
        this.directory.getNotes().remove(this);
        this.directory = null;
    }
}
