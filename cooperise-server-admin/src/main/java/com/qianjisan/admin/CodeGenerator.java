package com.qianjisan.admin;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MyBatis-Plus 3.5.5 代码自动生成器
 * 使用 FastAutoGenerator 新API
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 数据库配置
        String url = "jdbc:mysql://139.196.208.166:3306/deigo?useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "sy122812";

        // 项目配置
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "/src/main/java";
        String xmlOutputDir = projectPath + "/src/main/resources/mapper";

        // 需要生成的表名（可多个）
        List<String> tables = Arrays.asList("issue_detail", "template", "template_field"); // 替换为你的表名

        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("cooperise")        // 设置作者
                            .outputDir(outputDir)     // 指定输出目录
                            .disableOpenDir()         // 生成后不打开文件夹
                            .commentDate("yyyy-MM-dd") ;// 注释日期格式
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.qianjisan")     // 父包名
                            .moduleName("generator")       // 模块名
                            .entity("entity")         // 实体类包名
                            .service("service")       // Service包名
                            .serviceImpl("service.impl") // Service实现类包名
                            .mapper("mapper")         // Mapper包名
                            .controller("controller") // Controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutputDir)); // XML文件路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables)        // 设置需要生成的表名
                            // 实体类策略配置
                            .entityBuilder()
                            .naming(NamingStrategy.underline_to_camel) // 数据库表名下划线转驼峰
                            .columnNaming(NamingStrategy.underline_to_camel) // 数据库列名下划线转驼峰
                            .enableLombok()           // 启用Lombok
                            .logicDeleteColumnName("is_deleted") // 逻辑删除字段名
                            .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .enableTableFieldAnnotation() // 启用字段注解
                            // Controller策略配置
                            .controllerBuilder()
                            .enableRestStyle()        // 启用Rest风格
                            .enableHyphenStyle()     // 启用驼峰转连字符

                            // Service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 服务接口文件名格式
                            .formatServiceImplFileName("%sServiceImpl") // 服务实现类文件名格式

                            // Mapper策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation() // 启用@Mapper注解
                            .enableBaseResultMap()   // 启用BaseResultMap
                            .enableBaseColumnList(); // 启用BaseColumnList
                })
                // 模板引擎配置
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker模板引擎
                .execute();
    }
}