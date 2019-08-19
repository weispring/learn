package com.lxc.learn.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.json.Json;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class BitTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String KEY_SETBIT = "set_bit";


    /**
     * 签到/日活用户数
     * setbit
     */
    @Test
    public void testSerBit() {

        //redisTemplate.delete(KEY_SETBIT);
        Boolean result = redisTemplate.opsForValue().setBit(KEY_SETBIT, 0, true);
        log.info("{}",result );
        String v = redisTemplate.opsForValue().get(KEY_SETBIT);
        log.info("{}",v);
        Boolean r1 = redisTemplate.opsForValue().getBit(KEY_SETBIT,1);
        Boolean v0 = redisTemplate.opsForValue().getBit(KEY_SETBIT,0);
        Boolean r63 = redisTemplate.opsForValue().setBit(KEY_SETBIT,1,true);
        Boolean v63 = redisTemplate.opsForValue().setBit(KEY_SETBIT,63,false);
        //Boolean v64 = redisTemplate.opsForValue().getBit(KEY_SETBIT,64);


        log.info("", "");
    }

    @Autowired
    private Redisson redisson;

    @Test
    public void testLock() throws Exception{
        //是否可重入，守护线程续航实现
        RLock rLock = redisson.getLock("lock");
        boolean l = rLock.tryLock(10000, 10000,TimeUnit.MILLISECONDS);
        boolean l1 = rLock.tryLock(10000, 10000,TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().setIfAbsent("klock", "1");
        redisTemplate.opsForValue().set("kl", "", 10000);
        log.info("", "");
    }



    public static void main(String[] args) {


        String j = "{\n" +
                "\t\"interfaceId\": \"700001\",\n" +
                "\t\"columns\": [{\n" +
                "\t\t\t\"keywords\": [{\n" +
                "\t\t\t\t\t\"businessCode\": \"0017\",\n" +
                "\t\t\t\t\t\"language\": \"T\",\n" +
                "\t\t\t\t\t\"sceneId\": [],\n" +
                "\t\t\t\t\t\"commodityId\": [\"200233\", \"316259\", \"316262\", \"312692\", \"315606\", \"100909\"],\n" +
                "\t\t\t\t\t\"data\": [{\n" +
                "\t\t\t\t\t\t\t\"option\": \"門市編號\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"PT02\",\n" +
                "\t\t\t\t\t\t\t\"varName\": \"outlet_code\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"員工編號\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"P4872\",\n" +
                "\t\t\t\t\t\t\t\"varName\": \"staff_id\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"註冊用戶名稱\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"LI,\n" +
                "\t\t\t\t\t\t\tCHUN\",\n" +
                "\t\t\t\t\t\t\t\"varName\":\"reg_customer_name\",\n" +
                "\t\t\t\t\t\t\t\"tag\":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t流動電話號碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t69171104 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tmsisdn \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t客戶號碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t1.6615476 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tcustcode \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t合約生效日期 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t20190822 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tapplication_date \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t服務生效日期 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t20190819 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tactivation_date \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t身份證∕ 商業登記號碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t63750416 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tpassportno \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t電話號碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t9204818\n" +
                "\t\t\t\t\t\t\t1 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\treg_tel \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t請選擇日後通訊,\n" +
                "\t\t\t\t\t\t\t短訊及賬單的語言 ^ \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\tT \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tsms_lang \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t傳呼∕ 流動電話號碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t69171104 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tpager_mobile \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t傳真號碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\treg_home_fax \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t電郵地址 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\tli.xianchun @vpclub.cn \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tweb_bill_email \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t客戶密碼 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tmsg1 \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t登記地址 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t香港銅鑼灣記利佐治街百德\n" +
                "\t\t\t\t\t\t\t大廈 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tcomb_reg_addr1 \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t登記地址2 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tcomb_reg_addr2 \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t郵寄賬單地址 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t香港銅鑼灣記利佐治街百德大廈 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tcomb_bill_addr1 \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t郵寄賬單地址2 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tcomb_bill_addr2 \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t收信人 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tattention \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t智能咭編號 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tsim_no \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t服務計劃 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"『\n" +
                "\t\t\t\t\t\t\t飛常全球通』 - 環球服務計\n" +
                "\t\t\t\t\t\t\t劃 20 GB_Dealer Sales \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tservice_plan \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t服務月費 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t69800 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\taccessfee \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t免費通話時間(分鐘)\n" +
                "\t\t\t\t\t\t\t\",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tinter_intra \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t免費通話時間(分鐘) 1 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tfree_unit_desc \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t复选框： 申请IDD001服务 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t0 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tis_idd \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t复选框： HK$1000按金 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\t0 \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tis_deposit \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\tHK$1000按金豁免 ^ \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":n\n" +
                "\t\t\t\t\t\t\tull,\n" +
                "\t\t\t\t\t\t\t\"varName\": \"is_recurring\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"額外功能\",\n" +
                "\t\t\t\t\t\t\t\"value\": [{\n" +
                "\t\t\t\t\t\t\t\t\"開設紙質賬單寄送服務（65歲及以上長者申請，會在下個賬單月免除所繳費用）\": \"1000\"\n" +
                "\t\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\t\"varName\": \"service\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"table_json2\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"增值服务的费用\",\n" +
                "\t\t\t\t\t\t\t\"value\": null,\n" +
                "\t\t\t\t\t\t\t\"varName\": \"accessfee_1\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"截數日期為每月\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"22\",\n" +
                "\t\t\t\t\t\t\t\"varName\": \"cut_off_date\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"目標首個計帳截數日為\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"2019年8月22日\",\n" +
                "\t\t\t\t\t\t\t\"varName\": \"first_cut_off_date\",\n" +
                "\t\t\t\t\t\t\t\"tag\": \"\"\n" +
                "\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\"option\": \"與首個實際計帳截數日不足一整\n" +
                "\t\t\t\t\t\t\t個帳單月， 客戶於第一期所預繳而多付之服務月費(除行政費及增值服務收費(如有)),\n" +
                "\t\t\t\t\t\t\t中國移動香港 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tpro_rateplan_del_wording \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t根據該帳單月之日數按比例退回客戶之流動電話帳戶 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tis_existing_chg_own \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t除額外附加合約另有規定 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tunsubscribe_vas_tbl \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t\",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\ttmcode_header \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\ttable_json \"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t重要條款 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":\"\n" +
                "\t\t\t\t\t\t\tAddress Proof desclaration \",\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\taddress_proof_content \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t服務及產品詳情 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tproduct \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"},{\"\n" +
                "\t\t\t\t\t\t\toption \":\"\n" +
                "\t\t\t\t\t\t\t產品售後服務資訊 \",\"\n" +
                "\t\t\t\t\t\t\tvalue \":null,\"\n" +
                "\t\t\t\t\t\t\tvarName \":\"\n" +
                "\t\t\t\t\t\t\tprodcut_info \",\"\n" +
                "\t\t\t\t\t\t\ttag \":\"\n" +
                "\t\t\t\t\t\t\t\"}, {\n" +
                "\t\t\t\t\t\t\t\t\"option\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": null,\n" +
                "\t\t\t\t\t\t\t\t\"varName\": \"mkt_msg2_chn\",\n" +
                "\t\t\t\t\t\t\t\t\"tag\": \"F_608_mkt_msg2_chn_t1\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"option\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"1\",\n" +
                "\t\t\t\t\t\t\t\t\"varName\": \"mkt_msg2_chn_show\",\n" +
                "\t\t\t\t\t\t\t\t\"tag\": \"show_html\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"option\": \"自动续约\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": \"1\",\n" +
                "\t\t\t\t\t\t\t\t\"varName\": \"autoext_desc_type\",\n" +
                "\t\t\t\t\t\t\t\t\"tag\": \"show_html\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"option\": \"自动续约1\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": null,\n" +
                "\t\t\t\t\t\t\t\t\"varName\": \"autoext_desc_type_1\",\n" +
                "\t\t\t\t\t\t\t\t\"tag\": \"show_html\"\n" +
                "\t\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\t\"option\": \"\",\n" +
                "\t\t\t\t\t\t\t\t\"value\": null,\n" +
                "\t\t\t\t\t\t\t\t\"varName\": \"is_idd_auto_apply\",\n" +
                "\t\t\t\t\t\t\t\t\"tag\": \"show_html\"\n" +
                "\t\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t}], \"mobileNo\": \"69171104\", \"userNo\": \"P2522\", \"customerId\": \"6557801\", \"customerCode\": \"1.6615476\", \"outLet\": \"PT02\", \"serialId\": \"UM20190819255302\"\n" +
                "\t\t\t}]\n" +
                "\t}";
        j = j.replaceAll("\n", "");
        j = j.replaceAll(" ", "");
        j = j.replaceAll("\t", "");
        System.out.println(JSON.parseObject(j).toJSONString());
        System.out.println(Integer.MAX_VALUE/8/1024/1024);
        System.out.println(Integer.MAX_VALUE +"========"+ Long.MAX_VALUE);
    }
}
