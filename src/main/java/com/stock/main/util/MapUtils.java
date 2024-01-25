package com.stock.main.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MapUtils {

    /**
     * begin: ArrayNoteToHashMap
     * input: List<LinkedHashMap>
     * output: pojo list
     * note: method 2 (same as convertToPojoList)
     *
     * @param linkedHashMapList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertToPojoList(List<LinkedHashMap<String, Object>> linkedHashMapList, Class<T> clazz) {
        List<T> pojoList = new ArrayList<>();

        for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
            T pojo = linkedHashMapToPojo(linkedHashMap, clazz);
            pojoList.add(pojo);
        }

        return pojoList;
    }

    /**
     * convert single linkedHashMap to one pojo
     *
     * @param linkedHashMap
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T linkedHashMapToPojo(LinkedHashMap<String, Object> linkedHashMap, Class<T> clazz) {
        T pojo;
        try {
            pojo = clazz.getDeclaredConstructor().newInstance();
            for (String fieldName : linkedHashMap.keySet()) {
                Object value = linkedHashMap.get(fieldName);
                clazz.getDeclaredField(fieldName).set(pojo, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to map LinkedHashMap to POJO.", e);
        }
        return pojo;
    }

    /**
     * begin: ArrayNoteToHashMap
     * input: List<LinkedHashMap>
     * output: pojo list
     * note: method 2 (same as convertToPojoList)
     *
     * @param linkedHashMapList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertToPojoList2(List<LinkedHashMap<String, Object>> linkedHashMapList, Class<T> clazz) {
        List<T> pojoList = new ArrayList<>();

        for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
            try {
                T pojo = clazz.getDeclaredConstructor().newInstance();
                for (String fieldName : linkedHashMap.keySet()) {
                    Object value = linkedHashMap.get(fieldName);
                    clazz.getDeclaredField(fieldName).set(pojo, value);
                }
                pojoList.add(pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return pojoList;
    }
}
