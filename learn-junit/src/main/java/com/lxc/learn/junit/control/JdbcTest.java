package com.lxc.learn.junit.control;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@Slf4j
public class JdbcTest {

    public static volatile Connection connection;

    static {
        String url = "jdbc:mysql://localhost:3333/learn?useUnicode=true&allowMultiQueries=true&rewriteBatchedStatements=true&autoReconnect=true&serverTimezone=UTC";
        String userName = "root";
        String password = "123456!!";
        String driveName = "";
        if (url.contains("oracle")) {
            driveName = "oracle.jdbc.driver.OracleDriver";
        } else if (url.contains("mysql")) {
            driveName = "com.mysql.cj.jdbc.Driver";
        }
        try {
            Class.forName(driveName);
            connection = DriverManager.getConnection(url,userName,password);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    public static void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public <T> void executeSql(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            //ps.setString(0,"");
            boolean result = ps.execute();
            log.info("result:{}",result);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
       /*     try {
                ps.close();
            } catch (SQLException e) {
                log.error(e.getMessage(),e);
            }
            close();*/
        }
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO `learn`.`customer_info_1` (`id`, `cmhk_customer_id`, `company_name`, `last_name`, `cc_code`, `first_name`, `gender`, `birthday`, `certificate_type`, `certificate_code`, `contact_number`, `email`, `address_info`, `created_by`, `created_time`, `updated_by`, `updated_time`, `remark`, `deleted`, `customer_category`, `cn_last_name`, `certificate_address_info`, `main_cust_code`, `main_cust_pwd`, `br_no`, `customer_group`, `referrer_passportno`, `referrer_msisdn`, `sub_cust_id`) VALUES ('123456', NULL, '微品', '王', '1234', '大睿', '1', '20171102', '2', '440982199812055436', '75643829', '13692211045@163.com', '{\\\"region\\\":\\\"南山区😀😘\\\",\\\"road\\\":\\\"科发路\\\",\\\"house\\\":\\\"金融基地\\\",\\\"build\\\":\\\"金融科技大厦\\\",\\\"seat\\\":\\\"2\\\",\\\"floor\\\":\\\"9\\\",\\\"room\\\":\\\"CD\\\"}', NULL, '1510127377259', NULL, '1510127377259', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);";
        new JdbcTest().executeSql(sql);
    }

}
