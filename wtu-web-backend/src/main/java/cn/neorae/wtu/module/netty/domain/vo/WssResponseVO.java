package cn.neorae.wtu.module.netty.domain.vo;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import com.alibaba.fastjson2.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WssResponseVO {
    

    private String message;

    private Integer code;

    private Object data;

    private Boolean success;

    private LocalDateTime time;

    private Integer action;

    public static TextWebSocketFrame JOIN(Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getMessage(),
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getCode(),
                                data,
                                true,
                                LocalDateTime.now(),
                                NettyServerEnum.ActionEnum.JOIN.getType()
                        )));
    }

    public static TextWebSocketFrame ADD_TEAM(Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getMessage(),
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getCode(),
                                data,
                                true,
                                LocalDateTime.now(),
                                NettyServerEnum.ActionEnum.ADD_TEAM.getType()
                        )));
    }

    public static TextWebSocketFrame REMOVE_TEAM(Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getMessage(),
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getCode(),
                                data,
                                true,
                                LocalDateTime.now(),
                                NettyServerEnum.ActionEnum.REMOVE_TEAM.getType()
                        )));
    }

    public static TextWebSocketFrame TOGGLE_STATUS(Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getMessage(),
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getCode(),
                                data,
                                true,
                                LocalDateTime.now(),
                                NettyServerEnum.ActionEnum.TOGGLE_STATUS.getType()
                        )));
    }

    public static TextWebSocketFrame CONNECT_SUCCEED(Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getMessage(),
                                ResponseEnum.WSS_RESPONSE_SUCCESS.getCode(),
                                data,
                                true,
                                LocalDateTime.now(),
                                NettyServerEnum.ActionEnum.CONNECTION.getType()
                        )));
    }

    public static TextWebSocketFrame CONNECT_FAIL(ResponseEnum responseEnum) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                    new WssResponseVO(
                        responseEnum.getMessage(),
                        responseEnum.getCode(),
                        null,
                        false,
                        LocalDateTime.now(),
                        NettyServerEnum.ActionEnum.CONNECTION.getType()
                            )));
    }

    public static TextWebSocketFrame CONNECT_FAIL(ResponseEnum responseEnum, Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                responseEnum.getMessage(),
                                responseEnum.getCode(),
                                data,
                                false,
                                LocalDateTime.now(),
                                NettyServerEnum.ActionEnum.CONNECTION.getType()
                                )));
    }

}
