package com.codegen.service.impl;

import com.codegen.service.CodeGenerator;
import com.codegen.service.CodeGeneratorManager;
import com.codegen.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Model & Mapper 代码生成器
 * Created by zhh on 2017/09/20.
 */
@Slf4j
public class ModelAndMapperGenerator extends CodeGeneratorManager implements CodeGenerator {

    public void genCode(String tableName, String modelName) {
        Context initConfig = initConfig(tableName, modelName);
        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration cfg = new Configuration();
            cfg.addContext(initConfig);
            cfg.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(cfg, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Model 和  Mapper 生成失败!", e);
        }

       // if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
       //     throw new RuntimeException("Model 和  Mapper 生成失败, warnings: " + warnings);
       // }

        if (StringUtils.isNullOrEmpty(modelName)) {
            modelName = tableNameConvertUpperCamel(tableName);
        }

        log.info("{}.java 生成成功!", modelName);
        log.info("{}Mapper.java 生成成功!", modelName);
        log.info("{}Mapper.xml 生成成功!", modelName);
    }

    /**
     * 完善初始化环境
     *
     * @param tableName 表名
     * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
     */
    public Context initConfig(String tableName, String modelName) {
        Context context;
        try {
            context = initMybatisGeneratorContext();
            JavaModelGeneratorConfiguration javaModelGeneratorConfig = new JavaModelGeneratorConfiguration();
            javaModelGeneratorConfig.setTargetProject(PROJECT_PATH + JAVA_PATH);
            javaModelGeneratorConfig.setTargetPackage(MODEL_PACKAGE);
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfig);

            JavaClientGeneratorConfiguration javaClientGeneratorConfig = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfig.setTargetProject(PROJECT_PATH + JAVA_PATH);
            javaClientGeneratorConfig.setTargetPackage(MAPPER_PACKAGE);
            javaClientGeneratorConfig.setConfigurationType("XMLMAPPER");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfig);

            TableConfiguration tableConfig = new TableConfiguration(context);
            tableConfig.setTableName(tableName);
            tableConfig.setDomainObjectName(modelName);
            //tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
            context.addTableConfiguration(tableConfig);
        } catch (Exception e) {
            throw new RuntimeException("ModelAndMapperGenerator 初始化环境异常!", e);
        }
        return context;
    }
}
