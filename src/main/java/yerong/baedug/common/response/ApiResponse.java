package yerong.baedug.common.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse <T>{

    private ApiHeader header;
    private ApiBody body;

    private static int SUCCESS = 200;
    private static int CREATED = 201;


    public ApiResponse(ApiHeader header){
        this.header = header;
    }


    public static <T> ApiResponse<T> success(T data, ResponseCode responseCode) {
        return new ApiResponse<T>(new ApiHeader(SUCCESS, "SUCCESS"), new ApiBody(data, responseCode.getMessage()));
    }
    public static <T> ApiResponse<T> create(T data, ResponseCode responseCode) {
        return new ApiResponse<T>(new ApiHeader(CREATED, "CREATED"), new ApiBody(data, responseCode.getMessage()));
    }
    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return new ApiResponse<T>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()),new ApiBody<>(null, responseCode.getMessage() ));
    }
}
