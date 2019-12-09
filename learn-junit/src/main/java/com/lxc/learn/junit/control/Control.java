package com.lxc.learn.junit.control;

import com.lxc.learn.common.util.WebUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.aop.ApiLog;
import com.lxc.learn.junit.aop.Second;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:32
 */
@RestController
@Slf4j
public class Control {


    /**
     * 切面执行顺序取决于切面bean的 @Order
     * 如果要执行每一个切面方法，必须递归调用，proceed()；如果在一个切面方法种不调用proceed()方法，则后续拦截方法不会执行。
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/add")
    @ApiLog
    @Second
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b){
        if (true){
            throw new RuntimeException("pppp");
        }
        return 1000;
    }
}
