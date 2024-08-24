package cn.cycad.web.response.handle;

import cn.cycad.web.response.exception.BusException;
import cn.cycad.web.response.result.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一处理接口异常与返回值类型
 *
 * @author sucl
 * @date 2024/4/17 8:51
 * @since 1.0.0
 */
@RestControllerAdvice
public class ControllerAdviceHandle implements ResponseBodyAdvice {

    public ControllerAdviceHandle() {
        System.out.println(">>>>开始使用 ControllerAdviceHandle");
    }

    /**
     * 统一异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception exception){
        BusException busException;
        if( exception instanceof BusException asException){
            busException = asException;
        }else{
            busException = convertException( exception );
        }
        return ResponseResult.fail(busException.getCode(), busException.getMessage());
    }

    private BusException convertException(Exception exception) {
        return new BusException(exception.getMessage(), exception);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if( body instanceof ResponseResult){
            return body;
        }
        return ResponseResult.ok(body);
    }
}
