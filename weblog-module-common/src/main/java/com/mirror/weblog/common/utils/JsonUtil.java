package com.mirror.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mirror
 * @description: JSON工具类
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    public static String toJsonString(Object obj) {
        log.info("序列化对象{}",obj);
        try{
            return INSTANCE.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            return obj.toString();
        }
    }
}
