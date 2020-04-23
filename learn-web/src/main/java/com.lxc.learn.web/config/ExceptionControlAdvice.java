package com.lxc.learn.web.config;

import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/13 15:05
 */
@Slf4j
@ControllerAdvice
public class ExceptionControlAdvice {

    @ExceptionHandler(value = {Throwable.class})
    public Resp disposalException(HttpServletRequest request, HttpServletResponse response, Throwable throwable){
        log.info("{}", "ExceptionControlAdvice");
        log.error(throwable.getMessage(), throwable);
        if (throwable instanceof RuntimeBusinessException){
            RuntimeBusinessException exception = (RuntimeBusinessException) throwable;
            if (exception.getReturnCode() != null){
                return RespUtil.getRespByCode(((RuntimeBusinessException)throwable).getReturnCode());
            }
        }
        return RespUtil.fail(throwable.getMessage());
    }

}
