package com.lwn.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtil {

    /**
     * 将对象转换为json字符串
     *
     * @param o Object
     * @return
     */
    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }

    /**
     * 将json字符串转化为对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将json字符串转化为对象
     *
     * @param json
     * @param typeReference
     * @return
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * 将Json字符转换为List
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 将 json 转化为JSONObject
     *
     * @param json
     * @return
     */
    public static JSONObject jsonToJsonObject(String json) {
        return JSON.parseObject(json);
    }

    /**
     * 将简单Json对象转换成Map
     *
     * @param jsonStr json对象
     * @return Map对象
     */
    public static Map<String, Object> jsonToObjectMap(String jsonStr) {
        return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 将简单Json对象转换成Map
     *
     * @param jsonStr json对象
     * @return Map对象
     */
    public static Map<String, String> jsonToStrMap(String jsonStr) {
        return JSON.parseObject(jsonStr,
                new TypeReference<Map<String, String>>() {
                });
    }

    /**
     * 将Javabean转换为Map
     *
     * @param javaBean javaBean
     * @return Map对象
     * @throws Exception
     */
    public static Map<String, Object> javabeanToMap(Object javaBean) {
        Map<String, Object> result = new HashMap<>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, value);
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /**
     * 将json数组转成List<Map<String,Object>
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> jsonToListOfObjectMap(String json) throws Exception {
        List<Object> lists = jsonToList(json, Object.class);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Object obj : lists) {
            Map<String, Object> map = javabeanToMap(obj);
            maps.add(map);
        }
        return maps;
    }

    /**
     * 将json数组转成List<Map<String,String>
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> jsonToListOfStrMap(String json) throws Exception {
        List<Object> lists = jsonToList(json, Object.class);
        List<Map<String, String>> maps = new ArrayList<>();
        for (Object obj : lists) {
            String jsonStr = toJson(obj);
            Map<String, String> map = jsonToStrMap(jsonStr);
            maps.add(map);
        }
        return maps;
    }

    /**
     * clone 对象浅克隆
     *
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T t) {
        if (t == null) {

            return null;
        }
        String json = toJson(t);
        return (T) fromJson(json, t.getClass());
    }

}
