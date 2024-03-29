package cn.neorae.common.response;

import cn.neorae.common.enums.Enums;
import cn.neorae.common.enums.ResponseEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liuqi
 * @description 返回对象
 * @date 2023/9/29 11:03
 **/
@Data
public class ResponseVO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 返回数据
     */
    private T data;

    /**
     * @description constructor of responses without data
     * @param message 返回消息
     * @param code 返回代码
     */
    public ResponseVO(String message, Boolean success, Integer code)
    {
        this.setMessage(message);
        this.setCode(code);
        this.setSuccess(success);
        this.setTime(LocalDateTime.now());
    }

    /**
     * @description constructor of responses without data
     * @param message 返回消息
     * @param code 返回代码
     * @param data 返回数据
     */
    public ResponseVO(String message, Boolean success, Integer code, T data)
    {
        this.setMessage(message);
        this.setCode(code);
        this.setSuccess(success);
        this.setData(data);
        this.setTime(LocalDateTime.now());
    }


    /**
     * @description response succeed without data
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> completed(){
        return new ResponseVO<>(ResponseEnum.HTTP_RESPONSE_SUCCESS.getMessage(), Enums.Bool.TRUE.getType(), ResponseEnum.HTTP_RESPONSE_SUCCESS.getCode());
    }

    /**
     * @description response succeed without data
     * @param data 数据
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> wrapData(T data){
        return new ResponseVO<>(ResponseEnum.HTTP_RESPONSE_SUCCESS.getMessage(), Enums.Bool.TRUE.getType(), ResponseEnum.HTTP_RESPONSE_SUCCESS.getCode(), data);
    }

    /**
     * @description response failed without data
     * @param responseEnum 返回代码枚举
     * @param data 数据
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> wrapData(ResponseEnum responseEnum, T data) {
        return new ResponseVO<>(responseEnum.getMessage(), Enums.Bool.TRUE.getType(), responseEnum.getCode(), data);
    }

    /**
     * @description response failed without data
     * @param message 返回消息
     * @param code 返回代码
     * @param data 数据
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> wrapData(String message, Integer code, T data) {
        return new ResponseVO<>(message, Enums.Bool.TRUE.getType(), code, data);
    }

    /**
     * @description response failed without data
     * @param responseEnum 返回代码枚举
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> failed(ResponseEnum responseEnum){
        return new ResponseVO<>(responseEnum.getMessage(), Enums.Bool.FALSE.getType(), responseEnum.getCode());
    }

    /**
     * @description response failed without data
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> failed(){
        return new ResponseVO<>(ResponseEnum.FAILED.getMessage(), Enums.Bool.FALSE.getType(), ResponseEnum.FAILED.getCode());
    }

    /**
     * @description response failed without data
     * @param message 返回消息
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> argsNotSatisfied(String message){
        return new ResponseVO<>(message, Enums.Bool.FALSE.getType(), ResponseEnum.BAD_PARAM.getCode());
    }

    /**
     * @description response redirected, access required
     * @param route 重定向至前端路由
     * @return ResponseVO
     */
    public static  <T> ResponseVO<T> redirect(T route, ResponseEnum responseEnum){
        return new ResponseVO<>(responseEnum.getMessage(), Enums.Bool.FALSE.getType(), responseEnum.getCode(), route);
    }

}