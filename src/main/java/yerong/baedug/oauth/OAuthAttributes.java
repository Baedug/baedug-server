package yerong.baedug.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class OAuthAttributes {

    private String id;
    private String token;
    private String email;

    @Builder
    public OAuthAttributes(String id, String token, String email, String name) {
        this.id = id;
        this.token = token;
        this.email = email;
    }
    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        if (registrationId.contains("apple")) {
            return ofApple(attributes);
        }
        return null;
    }
    private static OAuthAttributes ofApple(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .id(String.valueOf(attributes.get("sub")))
                .email(String.valueOf(attributes.get("email")))
                .build();
    }

    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", id);
        attributes.put("token", token);
        attributes.put("email", email);
        return attributes;
    }
    public String getNameAttributeKey() {
        return "id"; // 여기에 사용자의 고유 ID를 반환하도록 수정해야 할 수도 있습니다.
    }
}
