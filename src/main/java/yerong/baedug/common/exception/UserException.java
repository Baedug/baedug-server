package yerong.baedug.common.exception;

import yerong.baedug.common.response.ResponseCode;

public class UserException extends BaseException{

    public UserException(ResponseCode responseCode){
        super(responseCode);
    }
}
