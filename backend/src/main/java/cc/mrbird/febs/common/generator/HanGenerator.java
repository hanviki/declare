package cc.mrbird.febs.common.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HanGenerator {
    // 数据库 URL
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/febs?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    // 数据库驱动
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    // 数据库用户名
    private static final String USERNAME = "root";
    // 数据库密码scm_d_plan
    private static final String PASSWORD = "123456";

    private static final String PageUrl = "D:/Project_SCM/XH_SCM/frontend/src/views/";

    public static void main(String[] args) {


        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy

        mpg.setTemplateEngine(new VelocityTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setActiveRecord(false);//序列化 true 继承父类
        //gc.setKotlin(true);//是否生成 kotlin 代码
        gc.setAuthor("viki");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setDriverName(DRIVER_NAME);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        mpg.setDataSource(dataSourceConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        //strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{"test"}); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 scm 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass("cc.mrbird.febs.common.controller.BaseController");
        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);//设置restful风格
        //strategy.setTableFillList()
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        //strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        // 包配置
        PackageConfig pc = new PackageConfig();
        //自定义模块名
        final String moduleName = "scm";
        pc.setModuleName(moduleName);
        pc.setParent("cc.mrbird.febs");//《==== 包名（自己手动设置）
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        //在生成页面时候 排除一些字段
        List<String> eliminateFiledsList = new ArrayList<>();
        eliminateFiledsList.add("COMMENTS");
        eliminateFiledsList.add("STATE");
        eliminateFiledsList.add("IS_DELETEMARK");
        eliminateFiledsList.add("CREATE_TIME");
        eliminateFiledsList.add("MODIFY_TIME");
        eliminateFiledsList.add("CREATE_USER_ID");
        eliminateFiledsList.add("MODIFY_USER_ID");

        //在生成页面时候  查询字段设置
        List<String> searchFiledsList = new ArrayList<>();
        searchFiledsList.add("name");
        searchFiledsList.add("STATE");
        searchFiledsList.add("CREATE_TIME");
        searchFiledsList.add("MODIFY_TIME");

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.eliminateFileds}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("eliminateFileds",eliminateFiledsList);
                map.put("searchFileds",searchFiledsList);
                this.setMap(map);
            }
        };
        // 自定义 xxListIndex.html 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/templatesMybatis/list.vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                // return projectPath + "/src/main/resources/" + moduleName + "/" + tableInfo.getEntityName() + "ListIndex.vue";
                return PageUrl + moduleName + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + ".vue";
            }
        });
        //   cfg.setFileOutConfigList(focList);
        //   mpg.setCfg(cfg);

        // 自定义  xxAdd.html 生成
        focList.add(new FileOutConfig("/templates/templatesMybatis/add.vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return PageUrl + moduleName + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Add.vue";
            }
        });
        //  cfg.setFileOutConfigList(focList);
        //  mpg.setCfg(cfg);

        //  自定义 xxUpdate.html生成
        focList.add(new FileOutConfig("/templates/templatesMybatis/edit.vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return PageUrl + moduleName + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Edit.vue";
            }
        });

        //  自定义 xxUpdate.html生成
        focList.add(new FileOutConfig("/templates/templatesMybatis/mapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/cc/mrbird/febs/" + moduleName + "/dao/" + tableInfo.getEntityName() + "Mapper.java";
            }
        });
        //  自定义 xxUpdate.html生成
        focList.add(new FileOutConfig("/templates/templatesMybatis/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + moduleName + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        /*TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);*/

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/templatesMybatis/controller.java.vm");
        tc.setService("/templates/templatesMybatis/service.java.vm");
        tc.setServiceImpl("/templates/templatesMybatis/serviceImpl.java.vm");
        tc.setEntity("/templates/templatesMybatis/entity.java.vm");
        // tc.setMapper("/templates/templatesMybatis/mapper.java.vm");
        //  tc.setXml("/templates/templatesMybatis/mapper.xml.vm");
        tc.setMapper("");
        tc.setXml("");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
    }
}
