package yerong.baedug.common.exception;

import yerong.baedug.common.response.ResponseCode;

public class HeartException extends BaseException{

    public HeartException(ResponseCode responseCode){
        super(responseCode);
    }
}
