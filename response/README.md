## jpa-spring-boot-response

  在web开发中，规范所有请求响应类型，不管是对前端数据处理，还是后端统一数据解析都是非常重要的。今天我们简单的方式实现如何实现这一效果

### 实现方式

1. 定义响应类型
```java
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
```

2. 定义统一的异常处理流程，通过`@RestControllerAdvice`与`@ExceptionHandler`注解可以对Controller中的异常统一处理
```java
@RestControllerAdvice
public class ControllerAdviceHandle {
    
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception exception) {
        BusException busException;
        if (exception instanceof BusException asException) {
            busException = asException;
        } else {
            busException = convertException(exception);
        }
        return ResponseResult.fail(busException.getCode(), busException.getMessage());
    }
}

```

3. 定义统一响应拦截，通过是实现接口`ResponseBodyAdvice`，这里可以和上面的异常一起处理
```java
public class ControllerAdviceHandle implements ResponseBodyAdvice {
    
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
```

4. 定义spring配置，实现自动装配

在resource目录添加自动注入配置`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`，这样通过引入jar就可以自动使用该配置
```
cn.cycad.web.response.ResponseConfig
```

### 应用示例

1. 比如现在有一个`User`实体，我们通过继承基类
```java
@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/{val}")
    public Object get(@PathVariable("val") String val) throws BusException {
        if( "1".equals(val) ){
            throw new BusException("参数错误");
        }
        return Map.of("val",val);
    }

}
```

2. 通过调用请求，可以看到不管是否异常，结果都是下面的格式
```json
{
  "code": "999",
  "message": null,
  "data": null
}

```

###  实现原理


### 实现扩展

