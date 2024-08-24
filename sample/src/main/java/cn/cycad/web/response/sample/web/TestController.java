package cn.cycad.web.response.sample.web;

import cn.cycad.web.response.exception.BusException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author sucl
 * @date 2024/5/8 21:38
 * @since 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     *
     * @param user
     * @return
     */
    @GetMapping("/{val}")
    public Object get(@PathVariable("val") String val) throws BusException {
        if( "1".equals(val) ){
            throw new BusException("参数错误");
        }
        return Map.of("val",val);
    }

}
