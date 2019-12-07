package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by www on 2019/8/13.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//使json序列化时候，key为空时消失
public class ServerResponse<T> implements Serializable{
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status, T data){
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status, String msg, T data){
        this.status = status;
        this.data = data;
        this.msg = msg;
    }
    private ServerResponse(int status, String msg){
            this.status = status;
            this.msg = msg;
        }

    @JsonIgnore
//    使之不在json序列化结果中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }


    public static <T> ServerResponse<T> createBysuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBysuccess(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBysuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBysuccess(String msg, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }



    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage){
        return new ServerResponse<T>(errorCode, errorMessage);
    }

}
