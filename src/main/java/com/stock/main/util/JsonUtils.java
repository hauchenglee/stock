package com.stock.main.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonUtils {

    private static final Logger log = Logger.getLogger(JsonUtils.class.getName());

    /**
     * Java Entity, also List<Entity> to Json
     *
     * @param obj:  target Entity, include Java Entity or List<Entity>
     * @param view: to specific which entity will be transformed
     * @return json
     * @throws JsonProcessingException
     */
    public static String mapObjectWithView(Object obj, Class<?> view) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        return mapper.writerWithView(view).writeValueAsString(obj);
    }

    /**
     * Json to Java Entity
     *
     * @param strJson: target json string
     * @param mClass:  to specific which entity will be transformed
     * @return Java Entity
     * @throws JsonProcessingException
     */
    public static Object readValue(String strJson, Class<?> mClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(strJson, mClass);
    }

    /***
     * Json to List<Entity> or Map<String, Entity>
     * @param strJson: target json string
     * @param typeReference: how to use->
     *                     first way: new TypeReference<List<Entity>>
     *                     second way: new TypeReference<HashMap<String, Entity>>() {}
     * @return
     *  first: List<Entity>, if pass list
     *  second: Map<String, Entity>, if pass map
     * @throws JsonProcessingException
     */
    public static Object readValue(String strJson, TypeReference<?> typeReference) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(strJson, typeReference);
    }

    /**
     * Json to JsonNode
     *
     * @param strJson: target json string
     * @return jsonNode
     * @throws JsonProcessingException
     */
    public static JsonNode readTree(String strJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(strJson);
    }

    /**
     * IteratorJsonFieldName
     *
     * @param jsonNode: target jsonNode
     * @return json fieldName (json key name)
     * @throws JsonProcessingException
     */
    public static List<String> IteratorJsonFieldName(JsonNode jsonNode) throws JsonProcessingException {
        List<String> keys = new ArrayList<>();

        // iterator json fieldName
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(e -> keys.add(e));
        return keys;
    }

    /**
     * input: arrayNode
     * output: jsonNode list
     * example:
     * {
     * [
     * {key: value},
     * {key: value},
     * {key: value},
     * {}, same as first object
     * ]
     * }
     * Tip:
     * 1. value is string or number or else
     * 2. convert [ {}, {}, {} ] <- convert this array node to List<LinkHashMap>
     * 3. every linkHashMap is a {} store in list
     * 4. 将大的 [] 变成 list，里面小的 {} 变成 linkedHashMap
     *
     * @param arrayNode
     * @return List<LinkedHashMap < String, Object>> -> {key: value}, {key: value}, {key: value}
     * @throws JsonProcessingException
     */
    public static List<LinkedHashMap<String, Object>> arrayNodeToHashMap(ArrayNode arrayNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(arrayNode.toString(), new TypeReference<>() {
        });
    }

    /**
     * input: arrayNode
     * output: jsonNode list
     * example:
     * {
     * "data": [
     * [value, value, value],
     * [value, value, value],
     * [value, value, value]...
     * ]
     * }
     * Tip:
     * 1. value is string or number or else
     * 2. convert first[], example "data":[] <- this array node
     * 3. every JsonNode is a [] store in list
     * 4. 将大的 [] 变成 list，里面小的 [] 变成 jsonNode
     *
     * @param arrayNode
     * @return ArrayList<JsonNode> -> [value, value, value], [value, value, value] within List<>
     * @throws JsonProcessingException
     */
    public static ArrayList<JsonNode> arrayNodeToJsonNodeList(ArrayNode arrayNode) throws JsonProcessingException {
        ArrayList<JsonNode> nodeList = new ArrayList<>();
        for (JsonNode jsonNode : arrayNode) {
            nodeList.add(jsonNode);
        }
        return nodeList;
    }

    /**
     * key: [string, string, string] to List<String>
     *
     * @param jsonString
     * @return List<String>
     * @throws JsonProcessingException
     */
    public static List<String> convertToListString(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, List.class);
    }

    /**
     * get value by fieldName
     * example:
     * {
     * "fieldName1": "value",
     * "fieldName2": "value"
     * }
     *
     * @param jsonString
     * @param fieldName
     * @return value: string
     * @throws JSONException
     */
    public static String readValueByFieldName(String jsonString, String fieldName) throws JSONException {
        // https://blog.csdn.net/sinat_31057219/article/details/77962305
        // 推荐使用optString
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.optString(fieldName);
    }
}
