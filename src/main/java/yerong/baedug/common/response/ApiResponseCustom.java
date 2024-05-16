package yerong.baedug.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApiResponseCustom<T>{

    private ApiHeader header;
    private ApiBody<T> body;

    private static int SUCCESS = 200;
    private static int CREATED = 201;


    public ApiResponseCustom(ApiHeader header){
        this.header = header;
    }
    public ApiResponseCustom(ApiHeader header, ApiBody body){
        this.header = header;
        this.body = body;
    }


    public static <T> ApiResponseCustom<T> success(T data, ResponseCode responseCode) {
        return new ApiResponseCustom<T>(new ApiHeader(SUCCESS, "SUCCESS"), new ApiBody<>(data, responseCode.getMessage()));
    }
    public static <T> ApiResponseCustom<T> success(ResponseCode responseCode) {
        return new ApiResponseCustom<T>(new ApiHeader(SUCCESS, "SUCCESS"), new ApiBody<>(responseCode.getMessage()));
    }
    public static <T> ApiResponseCustom<T> create(T data, ResponseCode responseCode) {
        return new ApiResponseCustom<T>(new ApiHeader(CREATED, "CREATED"), new ApiBody<>
                (data, responseCode.getMessage()));
    }
    public static <T> ApiResponseCustom<T> fail(ResponseCode responseCode) {
        return new ApiResponseCustom<T>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()),new ApiBody<>(null, responseCode.getMessage() ));
    }
}
