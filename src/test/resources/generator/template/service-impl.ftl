package ${basePackage}.service.impl;

import ${basePackage}.mapper.${modelNameUpperCamel}Mapper;
<#--import ${basePackage}.model.${sign}.${modelNameUpperCamel};-->
import ${basePackage}.service.${modelNameUpperCamel}Service;
<#--import ${basePackage}.service.AbstractService;-->
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ${author}
 * @time ${date}
 */
@Service
public class ${modelNameUpperCamel}ServiceImpl <#--extends AbstractService<${modelNameUpperCamel}>--> implements ${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
