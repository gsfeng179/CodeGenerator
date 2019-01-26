package com.codegen.main;

import com.codegen.service.CodeGeneratorManager;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 代码生成器启动项
 * <p>扩展自定义方法参考：https://www.cnblogs.com/se7end/p/9293755.html</>
 * Created by zhh on 2017/09/20.
 */
public class CodeGeneratorMain {


    /**
     * 支持批量生成。map的key为表名，value为实体名
     */
    public static void main(String[] args) {
        CodeGeneratorManager cgm = new CodeGeneratorManager();

        Map<String, String> map = Maps.newHashMap();
        map.put("pc_uac_user", "UacUser");
        cgm.genMuliCodeWithCustomName(map);
    }


}
