package yerong.baedug.domain.member;

import jakarta.persistence.*;
import lombok.*;
import yerong.baedug.domain.BaseTimeEntity;
import yerong.baedug.domain.directory.Directory;
import yerong.baedug.domain.heart.Heart;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider socialProvider;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Directory> directories = new ArrayList<>();

    public void setRole(Role role){
        this.role = role;
    }
    public void updateName(String username){
        this.username = username;
    }


}
