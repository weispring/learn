package com.lxc.learn;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.io.*;
import java.util.Properties;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/16 20:56
 * @Description:
 */
public class Generation {


    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        Properties properties = getConfig();

        String oPath = properties.getProperty("project.dir");//得到当前项目的路径
        gc.setOutputDir(oPath + "/src/main/java");   //生成文件输出根目录
        gc.setOpen(false);//生成完成后不弹出文件框
        gc.setFileOverride(true);  //文件覆盖
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(properties.getProperty("author"));// 作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);   //设置数据库类型，我是postgresql
        dsc.setDriverName(properties.getProperty("driverName"));
        dsc.setUsername(properties.getProperty("db.userName"));
        dsc.setPassword(properties.getProperty("db.password"));
        dsc.setUrl(properties.getProperty("jdbcUrl"));  //指定数据库
        autoGenerator.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);      // 表名生成策略
            // 需要生成的表
        if (properties.getProperty("tables") != null){
            strategy.setInclude(properties.getProperty("tables").split(","));
        }
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(properties.getProperty("project.package"));//根包
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("entity");
        pc.setXml("mapper.xml");
        autoGenerator.setPackageInfo(pc);
        // 执行生成
        autoGenerator.execute();
    }

    /**
     * 获取配置
     * @return
     */
    public static Properties getConfig(){
        String confile = new Generation().getClass().getClassLoader().getResource("config_plus.properties").getFile();
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(confile))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties conf = new Properties();
        try {
            conf.load(bf);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conf;
    }
}



