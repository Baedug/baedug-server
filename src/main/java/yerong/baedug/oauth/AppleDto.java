package yerong.baedug.oauth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppleDto {

    private String id;
    private String accessToken;
    private String refreshToken;
    private String email;
    private String username;
    private String idToken;
}
