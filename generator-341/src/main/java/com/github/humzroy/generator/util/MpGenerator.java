package com.github.humzroy.generator.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wuhengzhen
 * @date 2021/06/03 21:43
 **/

public class MpGenerator {

    /**
     * 用来获取Mybatis-Plus.properties文件的配置信息
     */
    final static ResourceBundle rb = ResourceBundle.getBundle("properties/mbg");

    /**
     * 从配置文件中获取要生成的表
     *
     * @return
     */
    private static String[] getTables() {
        String tables = rb.getString("tables");
        if (StringUtils.isEmpty(tables)) {
            throw new MybatisPlusException("要生成的表不能为空！");
        }
        System.out.println("要生成的表：" + tables);
        return tables.split(",");
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
     *
     * @param args
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("--------------------start generator-------------------");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = configGlobalConfig();
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = configDataSource();
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // pc.setModuleName(scanner("模块名"));
        pc.setModuleName(rb.getString("module.name"));
        pc.setParent(rb.getString("parent.package"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        //	        InjectionConfig cfg = new InjectionConfig() {
        //	            @Override
        //	            public void initMap() {		//注入自定义 Map 对象
        //	                // to do nothing
        //	            }
        //	        };
        //	        List<FileOutConfig> focList = new ArrayList<>();
        //	        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
        //	            @Override
        //	            public String outputFile(TableInfo tableInfo) {
        //	                // 自定义输入文件名称
        //	                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
        //	                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
        //	            }
        //	        });
        //	        cfg.setFileOutConfigList(focList);
        //	        mpg.setCfg(cfg);
        //	        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置	数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        StrategyConfig strategy = configStrategyConfig();
        // 是否生成实体时，生成字段注解
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        //==========[自定义配置]==========
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        injectionConfig.setFileCreate((configBuilder, fileType, filePath) -> {
            //检查路径，不存在的话，则生成
            //checkDir(filePath);
            if (new File(filePath).exists()) {
                //如果文件存在，且类型不为ENTITY，则不生成文件（避免覆盖）
                if (fileType != FileType.ENTITY && fileType != FileType.XML) {
                    //不允许生成模板文件
                    return false;
                }
            }
            // 允许生成模板文件
            return true;
        });
        mpg.setCfg(injectionConfig);

        // ==================  自定义模板配置： 默认配置位置 mybatis-plus/src/main/resources/templates  ======================
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setXml("/templates/mapper.xml")                                          // 设置生成xml的模板
                .setEntity("/templates/entity.java")                                // 设置生成entity的模板
                .setMapper("/templates/mapper.java")                                // 设置生成mapper的模板
                .setController("/templates/controller.java")                        // 设置生成service的模板
                .setService("/templates/service.java")                              // 设置生成serviceImpl的模板
                .setServiceImpl("/templates/serviceImpl.java");                     // 设置生成controller的模板
        mpg.setTemplate(tc);

        //自定义文件生成路径，包路径
        //这里调用customPackagePath方法，使用可以自己在内部灵活配置路径
        //如果不调用该方法、就会使用MyBatis-Plus默认的文件生成路径和包路径生成文件、但可以使用上面的PackageConfig做一些简单的配置
        customPackagePath(pc, mpg);

        mpg.execute();
        System.out.println("--------------------end generator-------------------");
    }

    /**
     * 自定义包路径，文件生成路径，这边配置更灵活
     * 虽然也可以使用InjectionConfig设置FileOutConfig的方式设置路径
     * 这里直接使用Map方式注入ConfigBuilder配置对象更加直观
     *
     * @param pc
     * @param mpg
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void customPackagePath(PackageConfig pc, AutoGenerator mpg) throws NoSuchFieldException, IllegalAccessException {

        String projectPath = rb.getString("output.path");
        String mavenPath = "\\src\\main\\java\\";
        String srcPath = projectPath + File.separator + mavenPath;

        String moduleName = pc.getModuleName();
        if (StringUtils.isEmpty(moduleName)) {
            moduleName = "";
        }

        /**
         * packageInfo配置controller、service、serviceImpl、entity、mapper等文件的包路径
         * 这里包路径可以根据实际情况灵活配置
         */
        Map<String, String> packageInfo = new HashMap<>();
        packageInfo.put(ConstVal.CONTROLLER, rb.getString("controller.package") + (StringUtils.isNotBlank(moduleName) ? StringPool.DOT + moduleName : ""));
        packageInfo.put(ConstVal.SERVICE, rb.getString("service.package") + (StringUtils.isNotBlank(moduleName) ? StringPool.DOT + moduleName : ""));
        packageInfo.put(ConstVal.SERVICE_IMPL, rb.getString("service.impl.package") + (StringUtils.isNotBlank(moduleName) ? StringPool.DOT + moduleName : ""));
        packageInfo.put(ConstVal.ENTITY, rb.getString("entity.package") + (StringUtils.isNotBlank(moduleName) ? StringPool.DOT + moduleName : ""));
        packageInfo.put(ConstVal.MAPPER, rb.getString("mapper.package") + (StringUtils.isNotBlank(moduleName) ? StringPool.DOT + moduleName : ""));
        packageInfo.put(ConstVal.MODULE_NAME, moduleName);

        /**
         * pathInfo配置controller、service、serviceImpl、entity、mapper、mapper.xml等文件的生成路径
         * srcPath也可以更具实际情况灵活配置
         * 后面部分的路径是和上面packageInfo包路径对应的源码文件夹路径
         * 这里你可以选择注释其中某些路径，可忽略生成该类型的文件，例如:注释掉下面pathInfo中Controller的路径，就不会生成Controller文件
         */
        Map pathInfo = new HashMap<>();
        pathInfo.put(ConstVal.CONTROLLER_PATH, srcPath + packageInfo.get(ConstVal.CONTROLLER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_PATH, srcPath + packageInfo.get(ConstVal.SERVICE).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, srcPath + packageInfo.get(ConstVal.SERVICE_IMPL).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.ENTITY_PATH, srcPath + packageInfo.get(ConstVal.ENTITY).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.MAPPER_PATH, srcPath + packageInfo.get(ConstVal.MAPPER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.XML_PATH, projectPath + File.separator + "\\src\\main\\resources\\mybatis\\" + moduleName);
        pc.setPathInfo(pathInfo);

        /**
         * 创建configBuilder对象，传入必要的参数
         * 将以上的定义的包路径packageInfo配置到赋值到configBuilder对象的packageInfo属性上
         * 因为packageInfo是私有成员变量，也没有提交提供公共的方法，所以使用反射注入
         * 为啥要这么干，看源码去吧
         */
        ConfigBuilder configBuilder = new ConfigBuilder(mpg.getPackageInfo(), mpg.getDataSource(), mpg.getStrategy(), mpg.getTemplate(), mpg.getGlobalConfig());
        Field packageInfoField = configBuilder.getClass().getDeclaredField("packageInfo");
        packageInfoField.setAccessible(true);
        packageInfoField.set(configBuilder, packageInfo);

        /**
         * 设置配置对象
         */
        mpg.setConfig(configBuilder);
    }

    /**
     * 全局配置
     *
     * @return
     */
    private static GlobalConfig configGlobalConfig() {
        String projectPath = rb.getString("output.path");

        GlobalConfig config = new GlobalConfig();
        // 是否支持AR模式
        config
                // .setActiveRecord(true)
                // 作者
                .setAuthor(rb.getString("author"))
                // 生成路径
                // .setOutputDir(projectPath + "/src/main/java/")
                .setOutputDir(projectPath + File.separator + "/src/main/java")
                // 是否覆蓋已有文件 默认值：false
                .setFileOverride(true)
                // 主键策略
                .setIdType(IdType.AUTO)
                // 实体命名方式  默认值：null 例如：%sEntity 生成 UserEntity
                .setEntityName("%s")
                // mapper 命名方式 默认值：null 例如：%sDao 生成 UserDao
                .setMapperName("%sMapper")
                // Mapper xml 命名方式   默认值：null 例如：%sDao 生成 UserDao.xml
                .setXmlName("%sMapper")
                // 设置生成的service接口的名字的首字母是否为I,例如IEmployeeService
                .setServiceName("I%sService")
                // service impl 命名方式  默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
                .setServiceImplName("%sServiceImpl")
                // controller 命名方式    默认值：null 例如：%sAction 生成 UserAction
                .setControllerName("%sController")
                // 生成基本的resultMap
                .setBaseResultMap(true)
                .setSwagger2(true)
                // 生成基本的SQL片段
                .setBaseColumnList(true)
                // 是否开启swagger
                .setSwagger2(true)
                // 生成后打开文件夹
                .setOpen(false).setDateType(DateType.ONLY_DATE);
        return config;
    }

    /**
     * 数据源配置
     *
     * @return
     */
    private static DataSourceConfig configDataSource() {
        DataSourceConfig dsConfig = new DataSourceConfig();
        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL).setDriverName(rb.getString("jdbc_driver")).setUrl(rb.getString("jdbc_url")).setUsername(rb.getString("jdbc_user")).setPassword(rb.getString("jdbc_password"))
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            System.out.println("转换类型：" + fieldType);
                            return DbColumnType.INTEGER;
                        }
                        if (fieldType.toLowerCase().contains("timestamp")) {
                            System.out.println("转换类型：" + fieldType);
                            return DbColumnType.TIMESTAMP;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
        return dsConfig;
    }

    /**
     * 策略配置
     *
     * @return
     */
    private static StrategyConfig configStrategyConfig() {

        StrategyConfig stConfig = new StrategyConfig();

        List<TableFill> tableFillList = new ArrayList<>();
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //如下是配置
        TableFill sysCreateTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill sysUpdateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        // TableFill sysCreateBy = new TableFill("create_by", FieldFill.INSERT);
        // TableFill sysUpdateBy = new TableFill("update_by", FieldFill.UPDATE);
        tableFillList.add(sysCreateTime);
        tableFillList.add(sysUpdateTime);
        // tableFillList.add(sysCreateBy);
        // tableFillList.add(sysUpdateBy);

        String baseController = rb.getString("base.controller");
        String baseService = rb.getString("base.service");
        String baseEntity = rb.getString("base.entity");
        // 全局大写命名
        stConfig.setCapitalMode(true)
                // 指定表名 字段名是否使用下划线
                //.setDbColumnUnderline(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //.setTablePrefix("tbl_")
                // 生成的表
                // .setInclude(new String[]{
                //         "pms_brand"
                // })
                // .setInclude(scanner("表名"))
                .setInclude(getTables()).setEntityBooleanColumnRemoveIsPrefix(true)
                //【实体】是否为lombok模型（默认 false
                .setEntityLombokModel(true)
                //生成 @RestController 控制器
                .setRestControllerStyle(true).setControllerMappingHyphenStyle(false)
                // 自定义实体，公共字段
                .setTableFillList(tableFillList)
                //strategy.setInclude("trace_breed_drugs");
                // 设置控制器父类
                .setSuperControllerClass(baseController)
                // 设置服务层父类
                .setSuperServiceClass(baseService)
                //.setSuperServiceImplClass("");
                // 设置实体父类
                .setSuperEntityClass(baseEntity);
        //开启逻辑删除
        //                .setLogicDeleteFieldName("is_deleted");

        return stConfig;
    }

}
