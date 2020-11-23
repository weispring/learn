package com.lxc.learn.doc.jdbc;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;

/**
 * jdbc demo
 *SUN公司为了简化、统一对数据库的操作，定义了一套Java操作数据库的规范，称之为JDBC。JDBC就是就是使用java代码(程序)发送sql语句的技术 !
 * JDBC全称为：Java Data Base Connectivity(java数据库连接), 它主要由接口组成。
 * 组成JDBC的2个包 : java.sql , javax.sql, 以上2个包已经包含在J2SE中，所以不用导入，开发这只需要导入JDBC的实现类即数据库驱动包。开发JDBC应用需要以上2个包的支持外，还需要导入相应JDBC的数据库实现(即数据库驱动)
 *
 */
@Slf4j
public class Jdbc {

    private static String url = "jdbc:mysql://localhost:3306/learn?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC";

    private static String user = "root";

    private static String password = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2.获取连接对象
            conn = DriverManager.getConnection(url, user, password);

            //3.创建Statement对象
            stmt = conn.createStatement();

            //准备预编译的sql
            String sql = "SELECT * FROM user";
            //4.执行sql
            ResultSet rs = stmt.executeQuery(sql);

            //5.处理结果集 rs
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                log.info("id: {} , name: {}", id, name);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally{
            //6.关闭资源
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
