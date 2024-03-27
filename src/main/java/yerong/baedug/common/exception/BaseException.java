package yerong.baedug.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import yerong.baedug.common.response.ResponseCode;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{

    private final ResponseCode responseCode;

    @Override
    public String getMessage(){
        return responseCode.getMessage();
    }

}
