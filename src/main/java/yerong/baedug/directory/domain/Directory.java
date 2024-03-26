package yerong.baedug.directory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import yerong.baedug.common.domain.BaseTimeEntity;
import yerong.baedug.member.domain.Member;
import yerong.baedug.note.domain.Note;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Directory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    @JsonIgnoreProperties("directories")
    private Member member;

    @OneToMany(mappedBy = "directory",  orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @Builder
    public Directory (String name, Member member){
        this.name = name;
        this.member = member;
    }

    public void update(String name){
        this.name = name;
    }

    public void setMember(Member member){
        this.member = member;
    }
    public void removeMember() {
        this.member.getDirectories().remove(this);
        this.member = null;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.notes.remove(note);
        note.setDirectory(null);
    }
}
