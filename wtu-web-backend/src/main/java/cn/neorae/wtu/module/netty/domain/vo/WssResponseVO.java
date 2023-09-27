package cn.neorae.wtu.module.netty.domain.vo;

import cn.neorae.common.enums.ResponseEnum;
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

    public static TextWebSocketFrame connect(String message, Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                message,
                                200,
                                data,
                                true,
                                LocalDateTime.now())));
    }

    public static TextWebSocketFrame fail(ResponseEnum responseEnum) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                    new WssResponseVO(
                        responseEnum.getMessage(),
                        responseEnum.getCode(),
                        null,
                        false,
                        LocalDateTime.now())));
    }

    public static TextWebSocketFrame fail(ResponseEnum responseEnum, Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                        new WssResponseVO(
                                responseEnum.getMessage(),
                                responseEnum.getCode(),
                                data,
                                false,
                                LocalDateTime.now())));
    }

    public static TextWebSocketFrame wrapData(Object data) {
        return new TextWebSocketFrame(
                JSON.toJSONString(
                    new WssResponseVO(
                        ResponseEnum.SUCCESS.getMessage(),
                        200,
                        data,
                        true,
                        LocalDateTime.now())));
    }

}
