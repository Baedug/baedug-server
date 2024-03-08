package yerong.baedug.oauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomEntity {

    private String id;
    private Object result;

    public CustomEntity(String id, Object result) {
        this.id = id;
        this.result  = result;
    }
}
