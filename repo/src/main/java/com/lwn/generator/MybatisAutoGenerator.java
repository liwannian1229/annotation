package com.lwn.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MybatisAutoGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //gc.setOutputDir(projectPath + "/repo/src/main/java/com/lwn/model");// 只是输出在某一个位置让你看一下
        gc.setAuthor("liwannian");
        gc.setOpen(false);// 是否打开输出目录
        gc.setFileOverride(true);//每次运行进行覆盖操作
        gc.setMapperName("%sMapper");//java mapper类命名后缀
        //gc.setDateType(DateType.ONLY_DATE);//mysql timestamp由java.util.Date类型映射
        gc.setDateType(DateType.TIME_PACK); // 时间采用java 8，（操作工具类：JavaLib => DateTimeUtils）
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columnList

        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/db?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("admin");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //设置文件的包名
        pc.setModuleName("");
        pc.setParent("");
        pc.setEntity("com.lwn.model.entity");
        pc.setMapper("com.lwn.model.mapper");
//        pc.setController("com.lwn.controller");
//        pc.setService("com.lwn.service");
        //设置不同类文件生成的路径
        HashMap<String, String> pathMap = new HashMap<>();
        pathMap.put(ConstVal.ENTITY, projectPath + "/repo/src/main/java/com/lwn/model/entity");
        pathMap.put(ConstVal.MAPPER, projectPath + "/repo/src/main/java/com/lwn/model/mapper");
        pathMap.put(ConstVal.XML, projectPath + "/repo/src/main/resources/mapping");
//        pathMap.put(ConstVal.CONTROLLER, projectPath + "/my-service/src/main/java/com/lwn/controller");
//        pathMap.put(ConstVal.SERVICE, projectPath + "/my-service/src/main/java/com/lwn/service");
        pc.setPathInfo(pathMap);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 逻辑删除字段
        strategy.setLogicDeleteFieldName("is_del");
        //需要生成的表,为空,生成所有的表,多个表传数组
        //strategy.setInclude("".split(","));
        strategy.setInclude(new String[]{"user_info"});

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        //不生成如下类型模板
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);

        mpg.setTemplate(templateConfig);

        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String mapperXmlPath = "/templates/mapper.xml.vm";
        String mapperJavaPath = "/templates/mapper.java.vm";
        String entityPath = "/templates/entity.java.vm";
//        String controllerPath = "/templates/controller.java.vm";
//        String servicePath = "/templates/service.java.vm";


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置优先于默认配置生效
        focList.add(new FileOutConfig(entityPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Entity文件名跟生成路径
                return projectPath + "/repo/src/main/java/com/lwn/model/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(mapperXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper.xml 文件名和生成路径
                return projectPath + "/repo/src/main/resources/mapping/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig(mapperJavaPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper类文件名和生成路径
                return projectPath + "/repo/src/main/java/com/lwn/model/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });

//        focList.add(new FileOutConfig(controllerPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义Controller类文件名和生成路径
//                return projectPath + "/my-service/src/main/java/com/lwn/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
//            }
//        });
//
//        focList.add(new FileOutConfig(servicePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义Service类文件名和生成路径
//                return projectPath + "/my-service/src/main/java/com/lwn/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
//            }
//        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        mpg.setStrategy(strategy);

        mpg.execute();
    }

}

