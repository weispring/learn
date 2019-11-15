package com.lxc.learn.jdk.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/15 11:45
 */
@Slf4j
public class RegexTest {


    /**
     * 当前用户{ecSubscriberInsId}存在在途订单{orderId}，不能进行当前操作。

     */
    @Test
    public void test(){
        String s = "当前用户{889989信息e33333}存在在途订单{111}";
        Pattern pattern = Pattern.compile("^([\\s\\S]{4})([\\s\\S]*)存在在途订单([\\s\\S]*)$");
        //Pattern pattern = Pattern.compile("当前用户(.*?)存在在途订单(.*?)，不能进行当前操作。");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()){
            int i = matcher.groupCount();
            //<=
            for (int j=0;j<=i;j++){
                String value = matcher.group(j);
                System.out.println(value);
            }
        }
    }


    @Test
    public void test1(){
        Pattern p=Pattern.compile("\\d+");
        Matcher m=p.matcher("我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com");
        while(m.find()) {
            System.out.println(m.group());
        }
    }


    @Test
    public void test2(){
        Pattern p=Pattern.compile("(\\{(.*?)\\})");
        Matcher m=p.matcher("我的QQ是:456456 我的电{0}话是:0532214 我的{2232}邮箱是:aaa123@aaa.com");
        while(m.find()) {
            System.out.println(m.group());
        }
       System.out.println("我的QQ是:456456 我的电{0}话是:0532214 我的{2232}邮箱是:aaa123@aaa.com".replaceAll("(\\{(.*?)\\})", "==="));

    }

    @Test
    public void test3(){
        String desc = "当前用户ecSubscriberInsId存在在途订单orderId，不能进行当前操作。";
        Pattern descPattern = Pattern.compile("当前用户(.*?)存在在途订单(.*?)，不能进行当前操作。");
        Matcher descmatcher = descPattern.matcher(desc);
        // descmatcher.find(); or descmatcher.matches();  必须执行一次，否则无法group();
        descmatcher.find();
        if (descmatcher.groupCount() == 2) {
            for (int i = 1; i <= 2; i++) {
                System.out.println(descmatcher.group(i));
            }
        }
    }

    @Test
    public void test4(){
        String desc = "<resultMap id=\"BaseResultMap\" type=\"cn.vpclub.cmhk.business.provider.entity.AbnormalOrderInfo\">\n" +
                "\t\t<id column=\"id\" property=\"id\" />\n" +
                "\t\t<result column=\"order_id\" property=\"orderId\" />\n" +
                "\t\t<result column=\"order_no\" property=\"orderNo\" />\n" +
                "\t\t<result column=\"register_type\" property=\"registerType\" />\n" +
                "\t\t<result column=\"transact_type\" property=\"transactType\" />\n" +
                "\t\t<result column=\"org_name\" property=\"orgName\" />\n" +
                "\t\t<result column=\"phone_no\" property=\"phoneNo\" />\n" +
                "\t\t<result column=\"customer_id\" property=\"customerId\" />\n" +
                "\t\t<result column=\"contact_number\" property=\"contactNumber\" />\n" +
                "\t\t<result column=\"register_time\" property=\"registerTime\" />\n" +
                "\t\t<result column=\"business_code\" property=\"businessCode\" />\n" +
                "\t\t<result column=\"business_name\" property=\"businessName\" />\n" +
                "\t\t<result column=\"contract_period\" property=\"contractPeriod\" />\n" +
                "\t\t<result column=\"payment_detail\" property=\"paymentDetail\" />\n" +
                "\t\t<result column=\"umall_total\" property=\"umallTotal\" />\n" +
                "\t\t<result column=\"pos_total\" property=\"posTotal\" />\n" +
                "\t\t<result column=\"email_status\" property=\"emailStatus\" />\n" +
                "\t\t<result column=\"status\" property=\"status\" />\n" +
                "\t\t<result column=\"interface_name\" property=\"interfaceName\" />\n" +
                "\t\t<result column=\"interface_request\" property=\"interfaceRequest\" />\n" +
                "\t\t<result column=\"interface_response\" property=\"interfaceResponse\" />\n" +
                "\t\t<result column=\"copy_email\" property=\"copyEmail\" />\n" +
                "\t\t<result column=\"desc\" property=\"desc\" />\n" +
                "\t\t<result column=\"created_by\" property=\"createdBy\" />\n" +
                "\t\t<result column=\"created_time\" property=\"createdTime\" />\n" +
                "\t\t<result column=\"updated_by\" property=\"updatedBy\" />\n" +
                "\t\t<result column=\"updated_time\" property=\"updatedTime\" />\n" +
                "\t\t<result column=\"remark\" property=\"remark\" />\n" +
                "\t\t<result column=\"deleted\" property=\"deleted\" />\n" +
                "\t</resultMap>";
        Pattern descPattern = Pattern.compile("column=\"(.*?)\"\\s*property=\"(.*?)\"");
        Matcher descmatcher = descPattern.matcher(desc);
        // descmatcher.find(); or descmatcher.matches();  必须执行一次，否则无法group();
        int i=0;
        while (descmatcher.find()) {
           if (i%2>0){
               System.out.print(descmatcher.group() + ":");
           }else {
               System.out.println(descmatcher.group() + "");
           }
            i++;
        }
    }


    /**
     * 匹配 协议 主机 端口 path
     */
    @Test
    public void test5(){
        String desc = "https://zhidao.baidu.com/question/454563566.html";
        Pattern descPattern = Pattern.compile("(.*?)://(.*?):?([\\d]*)/(.*?)");
        Matcher m = descPattern.matcher(desc);
        if (m.matches()) {
            int times = m.groupCount();
            for (int i = 1; i <= times; i++) {
                System.out.println(m.group(i));
            }
        }
        //第三组有可能为null
    }


}
