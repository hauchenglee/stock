package com.stock.main.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static List<String[]> parseTextToArray(String inputText) {
        // Define the regex pattern to match each element inside the text
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(inputText);

        // Create a list to store the extracted arrays
        List<String[]> resultList = new ArrayList<>();

        // Temporary list to store elements of each array
        List<String> tempArray = new ArrayList<>();

        // Loop through the matches and populate the result list
        while (matcher.find()) {
            tempArray.add(matcher.group(1));
            if (tempArray.size() == 9) {
                // Convert the temporary list to an array
                String[] arr = new String[9];
                for (int i = 0; i < 9; i++) {
                    arr[i] = tempArray.get(i);
                }
                // Add the array to the result list
                resultList.add(arr);
                // Clear the temporary list for the next array
                tempArray.clear();
            }
        }

        return resultList;
    }
}
