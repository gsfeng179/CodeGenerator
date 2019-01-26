package com.codegen.service;

import com.codegen.service.impl.ModelAndMapperGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class CleanCodePackageManager extends CodeGeneratorConfig {

    private static boolean flag;
    private static File file;

    public static void cleanMainPackage() {
        ModelAndMapperGenerator modelAndMapperGenerator = new ModelAndMapperGenerator();
        modelAndMapperGenerator.initConfig(null, null);
        deleteFolder(PROJECT_PATH + JAVA_PATH);
        deleteFolder(PROJECT_PATH + RESOURCES_PATH);
        log.info("clean ok!");
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    private static boolean deleteFolder(String sPath) {
        flag = false;
        file = new File(sPath);
        if (!file.exists()) {
            return flag;
        }

        if (file.isFile()) {
            return deleteFile(sPath);
        } else {
            // 为目录时调用删除目录方法
            return deleteDirectory(sPath);
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteFile(String sPath) {
        flag = false;
        file = new File(sPath);
        if (file.isFile() && file.exists()) {
            if (sPath.endsWith(".gitkeep")) {
                return true;
            }
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String sPath) {
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                flag = deleteFile(file1.getAbsolutePath());
                if (!flag) break;
            } else {
                flag = deleteDirectory(file1.getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) {
            return false;
        }
        if (sPath.endsWith("java\\") || sPath.endsWith("resources\\")) {
            return true;
        }
        return dirFile.delete();
    }
}
