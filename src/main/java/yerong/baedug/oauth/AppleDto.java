package yerong.baedug.oauth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppleDto {

    private String id;
    private String token;
    private String email;

}
