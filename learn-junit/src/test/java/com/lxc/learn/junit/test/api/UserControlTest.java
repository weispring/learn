package com.lxc.learn.junit.test.api;

import com.alibaba.fastjson.TypeReference;
import com.lxc.learn.common.util.HttpClientUtil;
import com.lxc.learn.common.util.WebTools;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.reflect.UnsafeUtils;
import com.lxc.learn.jdk.image.PictureUtil;
import com.lxc.learn.junit.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:59
 * @Description:
 */

@Slf4j
public class UserControlTest extends BaseTest {

    @Autowired
    protected WebApplicationContext applicationContext;

    @Test
    public void addUser() throws Exception {
        User user = new User();
        User o = this.postJson("/user/add", user,new TypeReference<User>(){});
        log.info("{}",o);
    }

    @Test
    public void getUserDetail() throws Exception {
        Req req = new Req();
        req.setBody(1229L);
        User o = this.postJson("/user/getUserDetail", req,new TypeReference<User>(){});
        log.info("{}",o);
    }


    @Test
    public void test() {
        String json = "{\"interfaceId\":\"700001\",\"columns\":[{\"keywords\":[{\"businessCode\":\"0017\",\"language\":\"T\",\"sceneId\":[],\"commodityId\":[\"313181\",\"100802\"],\"data\":[{\"option\":\"門市編號\",\"value\":\"PT02\",\"varName\":\"outlet_code\",\"tag\":\"\"},{\"option\":\"員工編號\",\"value\":\"P4872\",\"varName\":\"staff_id\",\"tag\":\"\"},{\"option\":\"註冊用戶名稱\",\"value\":\"LI,CHUN\",\"varName\":\"reg_customer_name\",\"tag\":\"\"},{\"option\":\"流動電話號碼\",\"value\":\"69347507\",\"varName\":\"msisdn\",\"tag\":\"\"},{\"option\":\"客戶號碼\",\"value\":\"1.9550479\",\"varName\":\"custcode\",\"tag\":\"\"},{\"option\":\"合約生效日期\",\"value\":\"20190821\",\"varName\":\"application_date\",\"tag\":\"\"},{\"option\":\"服務生效日期\",\"value\":\"20190821\",\"varName\":\"activation_date\",\"tag\":\"\"},{\"option\":\"身份證∕商業登記號碼\",\"value\":\"M714236(1)\",\"varName\":\"passportno\",\"tag\":\"\"},{\"option\":\"電話號碼\",\"value\":\"92048181\",\"varName\":\"reg_tel\",\"tag\":\"\"},{\"option\":\"請選擇日後通訊,短訊及賬單的語言^\",\"value\":\"T\",\"varName\":\"sms_lang\",\"tag\":\"\"},{\"option\":\"傳呼∕流動電話號碼\",\"value\":\"69347507\",\"varName\":\"pager_mobile\",\"tag\":\"\"},{\"option\":\"傳真號碼\",\"value\":null,\"varName\":\"reg_home_fax\",\"tag\":\"\"},{\"option\":\"電郵地址\",\"value\":\"li.xianchun@vpclub.cn\",\"varName\":\"web_bill_email\",\"tag\":\"\"},{\"option\":\"客戶密碼\",\"value\":null,\"varName\":\"msg1\",\"tag\":\"\"},{\"option\":\"登記地址\",\"value\":\"九龍旺角太子道西13座4樓5室\",\"varName\":\"comb_reg_addr1\",\"tag\":\"\"},{\"option\":\"登記地址2\",\"value\":null,\"varName\":\"comb_reg_addr2\",\"tag\":\"\"},{\"option\":\"郵寄賬單地址\",\"value\":\"九龍旺角太子道西13座4樓5室\",\"varName\":\"comb_bill_addr1\",\"tag\":\"\"},{\"option\":\"郵寄賬單地址2\",\"value\":null,\"varName\":\"comb_bill_addr2\",\"tag\":\"\"},{\"option\":\"收信人\",\"value\":null,\"varName\":\"attention\",\"tag\":\"\"},{\"option\":\"智能咭編號\",\"value\":\"89852121307217811752\",\"varName\":\"sim_no\",\"tag\":\"\"},{\"option\":\"服務計劃\",\"value\":\"4.5G 全速怀\n" +
                "쬥찦썥뙨舥ꃠ50GB-集客\",\"varName\":\"service_plan\",\"tag\":\"\"},{\"option\":\"服務月費\",\"value\":null,\"varName\":\"accessfee\",\"tag\":\"\"},{\"option\":\"免費通話時間 (分鐘)\",\"value\":null,\"varName\":\"inter_intra\",\"tag\":\"\"},{\"option\":\"免費通話時間 (分鐘)1\",\"value\":null,\"varName\":\"free_unit_desc\",\"tag\":\"\"},{\"option\":\"复选框：申请IDD001服务\",\"value\":\"0\",\"varName\":\"is_idd\",\"tag\":\"\"},{\"option\":\"复选框：HK$1000按金\",\"value\":\"0\",\"varName\":\"is_deposit\",\"tag\":\"\"},{\"option\":\"HK$1000按金豁免^\",\"value\":null,\"varName\":\"is_recurring\",\"tag\":\"\"},{\"option\":\"額外功能\",\"value\":[],\"varName\":\"service\",\"tag\":\"table_json2\"},{\"option\":\"增值服务的费用\",\"value\":null,\"varName\":\"accessfee_1\",\"tag\":\"\"},{\"option\":\"截數日期為每月\",\"value\":\"20\",\"varName\":\"cut_off_date\",\"tag\":\"\"},{\"option\":\"目標首個計帳截數日為\",\"value\":\"2019年09月20日\",\"varName\":\"first_cut_off_date\",\"tag\":\"\"},{\"option\":\"與首個實際計帳截數日不足一整個帳單月，客戶於第一期所預繳而多付之服務月費(除行政費及增值服務收費(如有)), 中國移動香港\",\"value\":null,\"varName\":\"pro_rateplan_del_wording\",\"tag\":\"\"},{\"option\":\"根據該帳單月之日數按比例退回客戶之流動電話帳戶\",\"value\":null,\"varName\":\"is_existing_chg_own\",\"tag\":\"\"},{\"option\":\"除額外附加合約另有規定\",\"value\":null,\"varName\":\"unsubscribe_vas_tbl\",\"tag\":\"\"},{\"option\":\"\",\"value\":{\"first_tbname\":\"服务计划:\",\"first_content\":[{\"commodityName\":\"4.5G 全速本地服務計劃 50GB-集客\",\"codeBySql\":\"100802\"}],\"second_tbname\":\"有關之額外附加合約:\",\"head\":[{\"commodityName\":\"產品/服務優惠代號\",\"codeBySql\":\"產品/服務合約編號\",\"commodityDes\":\"產品/服務優惠內容\",\"contractPeriod\":\"合約期\",\"penltyAmt\":\"提前終止費用\"}],\"second_content\":[{\"commodityName\":null,\"codeBySql\":\"313181\",\"commodityDes\":\"其他優惠\",\"contractPeriod\":\"24\",\"penltyAmt\":\"無費用\"}]},\"varName\":\"tmcode_header\",\"tag\":\"table_json\"},{\"option\":\"重要條款\",\"value\":null,\"varName\":\"address_proof_co\n" +
                "ntent\",\"tag\":\"F_930_address_proof_content_t\"},{\"option\":\"服務及產品詳情\",\"value\":null,\"varName\":\"product\",\"tag\":\"\"},{\"option\":\"產品售後服務資訊\",\"value\":null,\"varName\":\"prodcut_info\",\"tag\":\"\"},{\"option\":\"\",\"value\":\"1\",\"varName\":\"mkt_msg2_chn\",\"tag\":\"F_608_mkt_msg2_chn_t1\"},{\"option\":\"\",\"value\":\"1\",\"varName\":\"mkt_msg2_chn_show\",\"tag\":\"show_html\"},{\"option\":\"自动续约\",\"value\":\"0\",\"varName\":\"autoext_desc_type\",\"tag\":\"show_html\"},{\"option\":\"自动续约1\",\"value\":null,\"varName\":\"autoext_desc_type_1\",\"tag\":\"show_html\"},{\"option\":\"\",\"value\":\"0\",\"varName\":\"is_idd_auto_apply\",\"tag\":\"show_html\"}]}],\"mobileNo\":\"69347507\",\"userNo\":\"P4872\",\"customerId\":null,\"customerCode\":\"1.9550479\",\"outLet\":\"PT02\",\"serialId\":\"UM20190821de7e71\"}]}";

        json = json.replace("\t", "");
        json = json.replace("\n", "");
        json = json.replace(" ", "");
        System.out.println(json);
        List<User> list1= new ArrayList<>();
        User user;
        for (int i=0;i<3;i++){
            user = new User();
            user.setId(i);
            list1.add(user);
        }
        list1.get(2).setName("------");

        list1.get(1).setName("------");

        user = new User();
        user.setName("363636");
        Long start = System.currentTimeMillis();
        try{
            long offset = UnsafeUtils.getUnsafe().objectFieldOffset(User.class.getDeclaredField("name"));
            Object value = org.springframework.objenesis.instantiator.util.UnsafeUtils.getUnsafe().getObject(user, offset);

            log.info("zhi:{},{}", value.toString(),System.currentTimeMillis() - start);
        }catch (Exception e){
            e.printStackTrace();
        }

        int aa = list1.get(0).hashCode();
        int b = list1.get(1).hashCode();
        int c = list1.get(2).hashCode();
        List<User> list111 = list1.stream().distinct().collect(Collectors.toList());
        List<User> list2= new ArrayList<>();
        User a;
        for (User e : list1) {
            a = e;
            list2.add(a);
        }
        log.info("{}", list2);
        //org.apache.http.examples.client.ClientExecuteSOCKS.java
    }





    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        String url = CommonConstant.domain + "/test/testGet";
        for (int i = 0; i<500;i++){
            WebTools.get(url);
        }
        long c = System.currentTimeMillis() - s;
        Map map = new HashMap();
        long start = System.currentTimeMillis();
        for (int i = 0; i<500;i++){
            HttpClientUtil.invokeGet(url, map, "UTF-8" ,1000, 1000);
        }
        long a = System.currentTimeMillis() - start;
        log.info("新连接：{}", a);
        log.info("连接池 耗时：{}", c);
        //MainClientExec
    }

    @Test
    public void uploadFile() throws IOException {
        String result = null;
        String BOUNDARY = "letv"; // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL("https://devcloud.vpclub.cn/moses/upload/file/upload");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);

            BufferedImage bufferedImage = PictureUtil.loadImageLocal("C:\\Users\\vpclub\\Desktop\\企业微信截图_20200429180638.png");
            if (bufferedImage != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"fileData\"; filename=\""
                        + "企业微信截图_20200429180638.png" + "\"" + LINE_END);
                sb.append("Content-Type: application/ctet-stream" + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                dos.flush();
                ImageIO.write(bufferedImage,"",dos);
                dos.flush();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                log.info("uploadFile response code:" + res);
                // if(res==200)
                // {
                log.info("uploadFile request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                result = new String(result.getBytes("iso8859-1"), "utf-8");
                log.info("uploadFile result : " + result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
