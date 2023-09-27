package cn.neorae.wtu.module.netty.domain.vo;

import java.time.LocalDateTime;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import cn.neorae.common.enums.ResponseEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WssResponseVO {
    

    private String message;

    private Integer code;

    private Object data;

    private Boolean success;

    private LocalDateTime time;


    public static JSONObject fail(ResponseEnum responseEnum) {
        return JSON.parseObject(new TextWebSocketFrame(new WssResponseVO(responseEnum.getMessage(), responseEnum.getCode(), null, false, LocalDateTime.now()).toString()).text());
    }
    

}
