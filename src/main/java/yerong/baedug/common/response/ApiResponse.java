package yerong.baedug.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApiResponse <T>{

    private ApiHeader header;
    private ApiBody<T> body;

    private static int SUCCESS = 200;
    private static int CREATED = 201;


    public ApiResponse(ApiHeader header){
        this.header = header;
    }
    public ApiResponse(ApiHeader header, ApiBody body){
        this.header = header;
        this.body = body;
    }


    public static <T> ApiResponse<T> success(T data, ResponseCode responseCode) {
        return new ApiResponse<T>(new ApiHeader(SUCCESS, "SUCCESS"), new ApiBody<>(data, responseCode.getMessage()));
    }
    public static <T> ApiResponse<T> create(T data, ResponseCode responseCode) {
        return new ApiResponse<T>(new ApiHeader(CREATED, "CREATED"), new ApiBody<>
                (data, responseCode.getMessage()));
    }
    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return new ApiResponse<T>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()),new ApiBody<>(null, responseCode.getMessage() ));
    }
}
