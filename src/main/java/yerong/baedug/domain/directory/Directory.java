package yerong.baedug.domain.directory;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Not;
import yerong.baedug.domain.BaseTimeEntity;
import yerong.baedug.domain.member.Member;
import yerong.baedug.domain.note.Note;

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
    private Member member;

    @OneToMany(mappedBy = "directory")
    private List<Note> notes = new ArrayList<>();
}
