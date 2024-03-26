package yerong.baedug.heart.domain;


import jakarta.persistence.*;
import lombok.*;
import yerong.baedug.common.domain.BaseTimeEntity;
import yerong.baedug.member.domain.Member;
import yerong.baedug.note.domain.Note;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Heart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;


}
