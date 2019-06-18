package com.lxc.learn.webservice.simple.serve;

import lombok.extern.slf4j.Slf4j;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/16 14:36
 * @Description:
 */
@WebService(targetNamespace = "",wsdlLocation = "SimpleWebService.wsdl")
@Slf4j
public class SimpleWebService {

    /**
     * 定义webservice服务器中的方法
     * @param req
     * @return
     */
    public BuyResp simple(BuyReq req){
        log.info("入参：{}",req);

        BuyResp resp = new BuyResp();
        resp.setCode("1000");
        resp.setTotalMoney(101010L);
        return resp;
    }



    public static void main(String[] args){
        //定义自己的webservice服务器发布的地址
        String address = "http://localhost:9090/SimpleWebService"; //这个9090端口随便定义，只要不冲突即可
        //通过该方法进行发布
        Endpoint.publish(address, new SimpleWebService());
        //打印一句话，表示一下服务器进行了开启
        System.out.println("SimpleWebService starting");
    }


}
