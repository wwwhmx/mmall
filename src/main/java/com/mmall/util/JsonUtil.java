package com.mmall.util;

import com.mmall.pojo.TestPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by www on 2020/3/28.
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        //所有的日期格式都统一为以下的样式，既yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略在json字符串中存在，但是在java对象中不存在对应属性的情况，防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parse object to String error", e);
            return null;
        }
    }

    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parse object to String error", e);
            return null;
        }
    }

    //把字符串转化成对象！！
    public static <T> T stringToObj(String str, Class<T> clazz) {
        if(StringUtils.isEmpty(str) || clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("parse String to object error", e);
            return null;
        }
    }

    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if(StringUtils.isEmpty(str) || typeReference==null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? (T)str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("parse String to object error", e);
            return null;
        }
    }

    public static <T> T stringToObj(String str, Class<?> collectionClass, Class<?>... elementClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("parse String to object error", e);
            return null;
        }
    }


    public static void main(String[] args) {
        TestPojo testPojo = new TestPojo();
        String testPojoJson = JsonUtil.objToString(testPojo);
        log.info("testPojoJson:{}", testPojoJson);

       /* User u1 = new User();
        u1.setId(1);
        u1.setEmail("23193131004");
        u1.setCreateTime(new Date());

        User u2 = new User();
        u1.setId(2);
        u1.setEmail("2");

        String user1jsonPretty = JsonUtil.objToStringPretty(u1);
        String user1Json = JsonUtil.objToString(u1);

        log.info("user1jsonPretty{}", user1jsonPretty);
        log.info("user1Json{}", user1Json);

        List<User> userList =  Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        String userListStr = JsonUtil.objToStringPretty(userList);

        log.info("=========================");
        log.info(user1jsonPretty);

        User user = JsonUtil.stringToObj(user1Json, User.class);
        User user1 = JsonUtil.stringToObj(user1jsonPretty, User.class);

        List<User> userListObj = JsonUtil.stringToObj(userListStr, new TypeReference<List<User>>() { });


        List<User> userListObj1 = JsonUtil.stringToObj(userListStr, List.class, User.class);

        System.out.println("end");*/

    }




}











