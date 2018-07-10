package com.qFun.qFun.common.utils;


import org.apache.poi.ss.formula.functions.T;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelp {
	/**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    @SuppressWarnings("hiding")
	public static<T> Object JSONToObj(String jsonStr,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,
                    obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
