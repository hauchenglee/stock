package com.stock.main.util;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtils {

    public static File[] MultipleFileReader(String path, String extensionName) throws FileNotFoundException {
//        File folder = new File("./src/main/java/com/stock/main/util"); // 当前目录
        File folder = new File(path); // 当前目录
        File[] files = folder.listFiles((dir, name) -> name.endsWith(extensionName));
        if (files == null) {
            throw new FileNotFoundException();
        }
        return files;
    }
}
