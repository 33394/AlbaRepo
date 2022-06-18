package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

  private static ObjectMapper mapper = new ObjectMapper();

  {
    mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
  }


  /**
   * 将对象型转换为json字符串
   *
   * @param data
   * @return
   */
  public static String objectToJson(Object data) {
    try {
      return mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static JsonNode stringToJsonNode(String data) {

    try {
      return mapper.readTree(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T jsonToObj(Object jsonData, Class<T> beanType) {

    try {
      return mapper.readValue(mapper.writeValueAsString(jsonData), beanType);
    } catch (JsonProcessingException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }

  public static <T> T jsonToObj(String jsonData, Class<T> beanType) {

    try {
      return mapper.readValue(jsonData, beanType);
    } catch (JsonProcessingException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }

  public static <T> T jsonToList(String jsonData, Class<T> beanType) {

    JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      return mapper.readValue(jsonData, javaType);
    } catch (JsonProcessingException exception) {
      System.err.println(exception.getMessage());
    }
    return null;
  }

  public static <T> T jsonToList(Object jsonData, Class<T> beanType) {

    JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
    try {
      return mapper.readValue(mapper.writeValueAsString(jsonData), javaType);
    } catch (JsonProcessingException exception) {
      System.err.println(exception.getMessage());
    }
    return null;
  }

  public static Map<String, Object> jsonToMap(String jsonData) {
    try {
      return mapper.readValue(jsonData, Map.class);
    } catch (JsonProcessingException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }

  public static Map<String, Object> jsonToMap(Object jsonData) {
    try {
      return mapper.readValue(mapper.writeValueAsString(jsonData), Map.class);
    } catch (JsonProcessingException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }
}
