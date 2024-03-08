package yerong.baedug.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberRequestDto {
    private String socialId;
  //  private String username;
    private String email;
}
