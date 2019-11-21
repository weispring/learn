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
public class RegexTest2 {


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
        StringBuffer sb = new StringBuffer();

        while(m.find()) {//此处的find不能换成matches
            System.out.println(m.group());
            m.appendReplacement(sb,"==");
        }
        StringBuffer tail = new StringBuffer();
        m.appendTail(tail);

       System.out.println("我的QQ是:456456 我的电{0}话是:0532214 我的{2232}邮箱是:aaa123@aaa.com".replaceAll("(\\{(.*?)\\})", "==="));
       System.out.println(":sb"+sb);
       System.out.println(":tail:"+tail);
    }

    @Test
    public void test22(){
        Pattern p=Pattern.compile("(\\{(.*?)\\})");
        Matcher m=p.matcher("我的QQ是:456456 我的电{0}话是:0532214 我的{2232}邮箱是:aaa123@aaa.com");
        StringBuffer sb = new StringBuffer();
        //此时匹配不到
        if (m.matches()) {
            int times = m.groupCount();
            System.out.println(m.group());
            for (int i = 1; i <= times; i++) {
                System.out.println(m.group(i));
            }
        }
    }

    @Test
    public void test3(){
        String desc = "当前用户ecSubscriberInsId存在在途订单orderId，不能进行当前操作。1122454jkfdajjfl;d";
        Pattern descPattern = Pattern.compile("当前用户(.*?)存在在途订单(.*?)，不能进行当前操作。.*?");
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
        while (descmatcher.find()) {
            int times = descmatcher.groupCount();
            for (int i=1;i<=times;i++){
                if (i%2==1){
                    System.out.print(descmatcher.group(i)+": ");
                }else {
                    System.out.println(descmatcher.group(i));
                }
            }
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

    @Test
    public void testIsUrl(){
        String desc = "https://zhidao.baidu.com/question/454563566.html";
        Pattern descPattern = Pattern.compile("[https?|ftp|file]+://(.*?)");
        //"(ht|f)tp(s?)" 协议

        Matcher m = descPattern.matcher(desc);
        if (m.matches()) {
            System.out.println(m.group());
            int times = m.groupCount();
            for (int i = 1; i <= times; i++) {
                String a = m.group(i);
                System.out.println(a);
            }
        }
    }


    @Test
    public void testtest(){
        String desc = "https://zhidao.baidu.com/question/454563566.html";
        Pattern descPattern = Pattern.compile("([a-z])(.*?)");
        //"(ht|f)tp(s?)" 协议

        Matcher m = descPattern.matcher(desc);
        if (m.matches()) {
            int times = m.groupCount();
            for (int i = 1; i <= times; i++) {
                System.out.println(m.group(i));
            }
        }
    }

    /**
     * 正则中的<？>的作用
     * 1.   >.*<   得到的结果是  >link1</a>other content <a >link2<，能理解；
     *
     * 2.   >.*?<  得到的结果居然就是   >link1<  、>other content <   和 >link2<  ，这个“？”在这里起到了什么样的作用呢？
     *
     * 3.  >(.*)?< 我又加了一对括号，得到的结果和 “1”相同；
     */
    @Test
    public void test00(){
        String desc = "<a href=\"#\">link1</a>other content <a >link2</a>";
        Pattern descPattern = Pattern.compile(">(.*)?<");
        Matcher m = descPattern.matcher(desc);
        while (m.find()) {
            int times = m.groupCount();
            System.out.println(m.group());
            for (int i = 1; i <= times; i++) {
                System.out.println(m.group(i));
            }
        }
    }


    @Test
    public void test01(){
        //待匹配的字符串："Hello"Hi"Nice"Good
        String content="\"Hello\"Hi\"Nice\"Good";
        System.out.println(content);
        //匹配双引号的正则表达式 两种方式的差别
        String pattStr = "(?<=\").*?(?=\")";
        pattStr = "\"(.*?)\"";
        //创建Pattern并进行匹配
        Pattern pattern= Pattern.compile(pattStr);
        Matcher matcher=pattern.matcher(content);
        //将所有匹配的结果打印输出
        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }








}
