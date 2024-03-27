package yerong.baedug.common.exception;

import yerong.baedug.common.response.ResponseCode;

public class DirectoryException extends BaseException{

    public DirectoryException(ResponseCode responseCode){
        super(responseCode);
    }
}
