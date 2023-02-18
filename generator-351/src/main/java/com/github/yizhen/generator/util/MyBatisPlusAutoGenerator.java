package com.github.yizhen.generator.util;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * <p>
 * MyBatis-Plus代码生成器
 * </p>
 *
 * @author wuhengzhen
 * @custom.date 2020/6/2 15:58
 */
public class MyBatisPlusAutoGenerator {

    /**
     * 用来获取Mybatis-Plus.properties文件的配置信息
     */
    final static ResourceBundle rb = ResourceBundle.getBundle("properties/generator");

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
     * <p>
     * 读取控制台输入的表名
     * </p>
     *
     * @return 输入的表名
     * @author wuhengzhen
     * @custom.date 2020/7/1 15:56
     */
    @SuppressWarnings("resource")
    private static String scanner() {
        Scanner scanner = new Scanner(System.in);
        Console.log("请输入表名，多个表名用英文逗号分割：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的表名，多个英文逗号分割！");
    }

    /**
     * 代码生成入口
     * 注意： 请先修改resources目录下的generator.properties文件中的配置
     */
    public static void main(String[] args) {
        doGenerator();
    }

    private static void doGenerator() {
        // 1.数据库配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(rb.getString("database.url"), rb.getString("database.username"),
                rb.getString("database.password")).dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert() {
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
                })
                .keyWordsHandler(new MySqlKeyWordsHandler());

        // 1.1.快速生成器
        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(dataSourceConfigBuilder);

        // 2.全局配置
        // 覆盖已生成文件
        // 不打开生成文件目录
        // 指定输出目录,注意使用反斜杠\
        // 设置注释的作者
        // 设置注释的日期格式
        // 使用java8新的时间类型
        String projectPath = rb.getString("projectPath");
        fastAutoGenerator.globalConfig(globalConfigBuilder -> globalConfigBuilder.fileOverride().disableOpenDir()
                .outputDir(projectPath + "\\src\\main\\java")
                // 覆盖已有文件（已迁移到策略配置中，3.5.4版本会删除此方法）
                .fileOverride()
                .author(rb.getString("author"))
                // 注释日期   默认值: yyyy-MM-dd
                .commentDate("yyyy-MM-dd")
                .dateType(DateType.ONLY_DATE)
                // 开启 swagger 模式  默认值:false
                .enableSwagger()
        );
        // 3.包配置
        // 设置父包名
        // 设置父包模块名
        // 设置MVC下各个模块的包名
        // 设置XML资源文件的目录
        // 自定义输入文件名称
        String moduleName = rb.getString("module.name");
        if (StringUtils.isEmpty(moduleName)) {
            moduleName = "";
        }

        String finalModuleName = moduleName;
        fastAutoGenerator.packageConfig(packageConfigBuilder -> packageConfigBuilder
                // 父包名    默认值:com.baomidou
                .parent(rb.getString("packagePath"))
                // 父包名    默认值:com.baomidou
                // .moduleName(moduleName)
                .entity(rb.getString("entity.package")+ (StringUtils.isNotBlank(finalModuleName) ? StringPool.DOT + finalModuleName : ""))
                .mapper(rb.getString("mapper.package")+ (StringUtils.isNotBlank(finalModuleName) ? StringPool.DOT + finalModuleName : ""))
                .service(rb.getString("service.package")+ (StringUtils.isNotBlank(finalModuleName) ? StringPool.DOT + finalModuleName : ""))
                .serviceImpl(rb.getString("service.impl.package")+ (StringUtils.isNotBlank(finalModuleName) ? StringPool.DOT + finalModuleName : ""))
                .controller(rb.getString("controller.package")+ (StringUtils.isNotBlank(finalModuleName) ? StringPool.DOT + finalModuleName : "")).other("other")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "\\src\\main\\resources\\mybatis")));

        // 4.模板配置
        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        fastAutoGenerator.templateEngine(templateEngine);
        // 配置模板
        // WARN : 使用我们自定义模板 需要注意虽然我们文件是以ftl结尾，但是这里不要加上ftl,不然会报模板引擎找不到文件
        fastAutoGenerator.templateConfig(templateConfig -> templateConfig
                // 禁用模板
                // .disable(TemplateType.ENTITY,TemplateType.SERVICE...)
                // 禁用所有模板
                // .disable()
                .controller("")
                .entity(ConstVal.TEMPLATE_ENTITY_JAVA)
                .service(ConstVal.TEMPLATE_SERVICE)
                .serviceImpl(ConstVal.TEMPLATE_SERVICE_IMPL)
                .mapper(ConstVal.TEMPLATE_MAPPER)
                .mapperXml(ConstVal.TEMPLATE_XML));

        // 5.注入配置 TODO

        // 6.策略配置
        // 设置需要生成的表名
        // 设置过滤表前缀
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.enableCapitalMode()
                        .enableSkipView().disableSqlFilter().addInclude(getTables())
                // .addTablePrefix(TABLE_PREFIX)
        );

        // 6.1.Entity策略配置
        // 生成实体时生成字段的注解，包括@TableId注解等
        // 数据库表和字段映射到实体的命名策略，为下划线转驼峰
        // 全局主键类型为AUTO
        // 实体名称格式化为XXXEntity

        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新机制，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        Column sysCreateTime = new Column("create_time", FieldFill.INSERT);
        Column sysUpdateTime = new Column("update_time", FieldFill.INSERT_UPDATE);
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.entityBuilder()
                        // 开启生成实体时生成字段注解	默认值:false
                        // .enableTableFieldAnnotation()
                        // 数据库表映射到实体的命名策略	默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                        .naming(NamingStrategy.underline_to_camel)
                        // 数据库表字段映射到实体的命名策略	默认为 null，未指定按照 naming 执行
                        .columnNaming(NamingStrategy.underline_to_camel)
                        // 全局主键类型
                        .idType(IdType.AUTO)
                        // 开启 lombok 模型	默认值:false
                        .enableLombok()
                        // 开启链式调用
                        .enableChainModel()
                        // 开启生成实体时生成字段注解	默认值:false
                        .enableRemoveIsPrefix()
                        //如下是配置
                        .addTableFills(sysCreateTime, sysUpdateTime)
                        // 设置实体父类
                        .superClass(rb.getString("base.entity"))
                // .formatFileName("%sEntity")
        );

        // 如果不需要生成注解，去掉.enableTableFieldAnnotation()

        // 6.2.Controller策略配置
        // 开启生成@RestController控制器
        fastAutoGenerator
                .strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.controllerBuilder()
                                .formatFileName("%sController")
                                .enableRestStyle()
                        // .superClass()
                );

        // 6.3.Service策略配置
        // 格式化service接口和实现类的文件名称，去掉默认的ServiceName前面的I
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.serviceBuilder()
                .formatServiceFileName("I%sService").formatServiceImplFileName("%sServiceImpl")
                // 设置服务层父类
                .superServiceClass(rb.getString("base.service")));

        // 6.4.Mapper策略配置
        // 格式化 mapper文件名,格式化xml实现类文件名称
        fastAutoGenerator.strategyConfig(strategyConfigBuilder -> strategyConfigBuilder.mapperBuilder()
                .formatMapperFileName("I%sMapper").formatXmlFileName("%sMapper")
                // 启用 BaseResultMap 生成	默认值:false
                .enableBaseResultMap()
                // 启用 BaseColumnList	默认值:false
                .enableBaseColumnList()
        );

        // 7.生成代码
        fastAutoGenerator.execute();
    }
}
