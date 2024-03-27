package yerong.baedug.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiBody <T>{

    private T data;
    private T message;

}
