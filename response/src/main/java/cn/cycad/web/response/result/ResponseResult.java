package cn.cycad.web.response.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sucl
 * @date 2024/4/17 8:52
 * @since 1.0.0
 */
@Getter
@Setter
public class ResponseResult<T> {

    private static final String SUCCESS_CODE = "000";
    private static final String FAILURE_CODE = "999";

    private String code;

    private String message;

    private T data;


    public static <T> ResponseResult<T> ok(T data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(SUCCESS_CODE);
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult fail(String code, String message){
        if( code == null ){
            code = FAILURE_CODE;
        }
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult fail(String message){
        return fail(FAILURE_CODE, message);
    }
}
