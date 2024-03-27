package yerong.baedug.common.exception;

import yerong.baedug.common.response.ResponseCode;

public class NoteException extends BaseException{

    public NoteException(ResponseCode responseCode){
        super(responseCode);
    }
}
