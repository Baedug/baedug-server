package yerong.baedug.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiBody <T>{

    private T data;
    private T message;

    public ApiBody(T message){
        this.message = message;
    }

}
