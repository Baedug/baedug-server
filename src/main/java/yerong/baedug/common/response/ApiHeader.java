package yerong.baedug.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiHeader {
    private int resultCode;
    private String message;

}
