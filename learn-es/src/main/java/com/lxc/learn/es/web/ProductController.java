package com.lxc.learn.es.web;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.es.model.request.AccountReqBody;
import com.lxc.learn.es.service.impl.AccountServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bank/account")
@Api(value = "商品搜索", description = "账号搜索API-SERVICE")
public class ProductController {

    @Autowired
    private AccountServiceImpl accountService;

    /**
     * 搜索接口
     *
     * @return
     */
    @ApiOperation("搜索")
    @RequestMapping(value = "/keyword", method = RequestMethod.POST)
    public Resp keyword(@RequestBody AccountReqBody param) {
        log.info("商品搜索-{}", param);
        return accountService.keyWord(param);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Resp search(@RequestBody AccountReqBody param) {
        log.info("商品搜索-{}", param);
        return accountService.search(param);
    }

}
