package com.example.mqttserver.error;



import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */


    /* 403 FORBIDDEN : 권한이 없는 사용자 */


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    STATUS_NOT_CHANGED(HttpStatus.CONFLICT, "상태가 변경되지 않았습니다. 통신 상태를 확인하세요"),

    /* 500 INTERNAL_SERVER_ERROR : 서버오류 */

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}

