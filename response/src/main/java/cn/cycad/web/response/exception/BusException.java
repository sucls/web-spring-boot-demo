package cn.cycad.web.response.exception;

import org.springframework.core.NestedCheckedException;

/**
 * @author sucl
 * @date 2024/8/8 20:45
 * @since 1.0.0
 */
public class BusException extends NestedCheckedException {

    private String code = "999";
    private String message;

    public BusException(String msg) {
        super(msg);
        this.message = msg;
    }

    public BusException(String code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public BusException(String msg, Throwable cause) {
        super(msg, cause);
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
