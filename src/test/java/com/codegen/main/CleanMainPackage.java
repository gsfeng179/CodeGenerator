package com.codegen.main;

import com.codegen.service.CleanCodePackageManager;

public class CleanMainPackage {
    public static void main(String[] args) {
        // 清空src下java、resources包
        CleanCodePackageManager.cleanMainPackage();
    }
}
