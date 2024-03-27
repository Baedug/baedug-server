package yerong.baedug.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum ResponseCode {

    //400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    //403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    //405 Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다."),
    //500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다."),


    /**
     * user response
     */
    //404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다,"),

    //200 OK
    USER_READ_SUCCESS(HttpStatus.OK, "사용자 정보 조회 성공"),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),

    //201 Created
    USER_CREATE_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),


    /**
     * directory response
     */
    //404 Not Found
    DIRECTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "디렉토리를 찾을 수 없습니다,"),

    //200 OK
    DIRECTORY_READ_SUCCESS(HttpStatus.OK, "디렉토리 조회 성공"),
    DIRECTORY_UPDATE_SUCCESS(HttpStatus.OK, "디렉토리 수정 성공"),
    DIRECTORY_DELETE_SUCCESS(HttpStatus.OK, "디렉토리 삭제 성공"),

    //201 Created
    DIRECTORY_CREATE_SUCCESS(HttpStatus.CREATED, "디렉토리 생성 성공"),

    /**
     * note response
     */
    //404 Not Found
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "노트를 찾을 수 없습니다,"),

    //200 OK
    NOTE_READ_SUCCESS(HttpStatus.OK, "노트 조회 성공"),
    NOTE_UPDATE_SUCCESS(HttpStatus.OK, "노트 수정 성공"),
    NOTE_DELETE_SUCCESS(HttpStatus.OK, "노트 삭제 성공"),


    //201 Created
    NOTE_CREATE_SUCCESS(HttpStatus.CREATED, "노트 생성 성공"),

    /**
     * heart response
     */
    //404 Not Found
    HEART_NOT_FOUND(HttpStatus.NOT_FOUND, "하트를 찾을 수 없습니다,"),

    //200 OK
    HEART_READ_SUCCESS(HttpStatus.OK, "하트 정보 조회 성공"),
    HEART_DELETE_SUCCESS(HttpStatus.OK, "하트 삭제 성공"),

    //201 Created
    HEART_CREATE_SUCCESS(HttpStatus.CREATED, "하트 생성 성공");


    private HttpStatus httpStatus;
    private String message;

    public int getHttpStatusCode(){
        return httpStatus.value();
    }

}
