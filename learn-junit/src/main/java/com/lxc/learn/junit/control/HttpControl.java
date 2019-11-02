package com.lxc.learn.junit.control;

import com.lxc.learn.common.constant.SystemConstant;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/1 18:39
 */
@RestController
@Slf4j
public class HttpControl {

    /**
     * 请求已经被实现，而且有一个新的资源已经依据请求的需要而建立，
     * 且其 URI 已经随Location 头信息返回。假如需要的资源无法及时建立的话，应当返回 '202 Accepted'。
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/httpStatus201")
    public Object upload() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode("测试201.txt", SystemConstant.UTF8));
        byte[] bytes = null;
        try {
            bytes = "测试http201".getBytes();
        } catch (Exception e) {
            log.error("read file to byteArray error:{}", e);
        }
        return new ResponseEntity(bytes, headers, HttpStatus.CREATED);
    }


}
