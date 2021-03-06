package com.lxc.learn.junit.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lxc.learn.common.util.HttpClientUtil;
import com.lxc.learn.common.util.JsonUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.junit.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.druid.filter.config.ConfigTools;
/**
 * @author lixianchun
 * @Description
 * @date 2019/6/26 15:19
 */
@Slf4j
public class TestMain {

    private static final String url = "http://localhost:8081/user";
    private static final String host = "http://localhost:8081/";
    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);
    public static void main(String[] args) throws Exception{

        String proxySet = System.getProperty("java.wirelessnet.useSystemProxie");

        String[] str={"@wULiuMOBAN123"};
        ConfigTools.main(str);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (int i =0;i<1;i++){
            JSONObject object = new JSONObject();
            object.put("userName", "2222");
            jsonArray.add(object);
        }
        jsonObject.put("arr", jsonArray);

        JSONArray array = jsonObject.getJSONArray("arr");


        String s = JSONArray.toJSONString(array);
        s = array.toString();
        List<User> list = JSON.parseArray(s, User.class);


        String json = "{\"userName\":\"nn\",\"id\":\"123\"}";
        User user = JSON.parseObject(json, User.class);


        log.info(JsonUtil.objectToJson(new User()));
        //org.apache.http.conn.ConnectTimeoutException
        MDC.put("traceId", "LXC0214");
        log.info("测试日志中是否有当前自定义log");
        logger.info("测试日志中是否有当前自定义log");
        Map map = new HashMap();
        map.put("1","2");
        HttpClientUtil.postJsonBody(url + "/test",map,"UTF-8");

        //测试超时和无响应 1 超时表示超过指定时间无返回 2 无响应表示返回为空
        HttpClientUtil.postJsonBody(url + "/testSleep10s",map,"UTF-8");
    }


    @Test
    public void testUpload() throws Exception{
        String uri = "http://localhost:8080/file/upload";
        Map header = new HashMap();
        header.put("test01", "001");
        log.info("当前路径：{}", new File("./").getAbsolutePath());
        Map body = new HashMap();
        body.put("test02", "002");
        String result = HttpClientUtil.upload(uri, header, body, new File("./"+File.separator+"bossbr上台接口.xlsx"), "file");
        log.info("结果：{}", result);
    }

    @Test
    public void testDown() throws Exception{
        String uri = "http://localhost:8080/file/down";
        Map header = new HashMap();
        header.put("test1", "001");


        HttpClientUtil.down(uri, header, "./", "down.xlsx");
    }














    @Data
    @Accessors(chain = true)
    public static class User{

        private String userName;

        //不进行序列化，亦不进行反序列化
        @JsonIgnore
        private Long userId;

        private String test;


        private Info info;

        @Data
        @Accessors(chain = true)
        public static class Info{
            private Long id;

            private String name;
        }
    }




    @Test
    public void testDeserial(){
        log.info("{}", System.getProperty("java.io.tmpdir"));

          /*  String json =  "{\"id\":1148202247656747009,\"subCustId\":null,\"cmhkCustomerId\":\"23010000020854\",\"companyName\":\"99\",\"lastName\":\"REN\",\"firstName\":\"JIE\",\"ccCode\":null,\"gender\":1,\"birthday\":\"958780800000\",\"certificateType\":2,\"certificateCode\":\"12312312\",\"contactNumber\":\"92048181\",\"email\":\"ren.jie@vpclub.cn\",\"addressInfo\":\"{\"area\":null,\"district\":null,\"streetno\":null,\"streetName\":null,\"estateInput\":null,\"buildingInput\":null,\"blockInput\":null,\"blockBox\":null,\"floorInput\":null,\"floorBox\":null,\"flatInput\":null,\"flatBox\":null,\"detailedAddress\":null}\",\"createdBy\":null,\"createdTime\":1562587723065,\"updatedBy\":null,\"updatedTime\":1562587723065,\"remark\":null,\"deleted\":1,\"customerCategory\":null,\"cnLastName\":\"\346\270\254\350\251\246\",\"certificateAddr\":null,\"certificateAddrVO\":null,\"mainCustCode\":null,\"mainCustPwd\":null,\"brNo\":\"12312312\",\"customerGroup\":null,\"referrerPassportno\":null,\"referrerMsisdn\":null,\"query\":false,\"addressInfoVO\":null}";
            cn.vpclub.moses.common.api.dto.register.CustomerInfo customerInfo = JSONObject.parseObject(json, cn.vpclub.moses.common.api.dto.register.CustomerInfo.class);
*/
            log.info("{}", 1);

    }

}
