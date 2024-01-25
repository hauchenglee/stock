package com.stock.main.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {
    public static void main(String[] args) throws FileNotFoundException {
        File[] files = FileUtils.MultipleFileReader(Constants.stock_history_linux, ".csv");
        for (File file : files) {
            CsvUtils.csvReader(file);
        }
    }

    public static List<List<String>> csvReader(File csvFile) {
        List<List<String>> csvDataList = new ArrayList<>(); // 用于存储单个 CSV 文件的数据

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            String csvSplitBy = ","; // CSV 文件中的分隔符

            while ((line = br.readLine()) != null) {
                String[] values = splitCsvFields(line);
                List<String> rowValues = new ArrayList<>(Arrays.asList(values));
                csvDataList.add(rowValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvDataList;
    }

    public static String[] splitCsvFields(String line) {
        String[] values = line.split(",", -1);
        return values;
    }
}
