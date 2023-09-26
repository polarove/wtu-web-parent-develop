package cn.neorae.wtu.module.netty.domain.vo;

import java.time.LocalDateTime;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WssResponseVO {
    

    private Object data;

    private Boolean success;

    private String message;

    private LocalDateTime time;

    public static TextWebSocketFrame fail(String message) {
        return new TextWebSocketFrame(
                new WssResponseVO(null, false,message, LocalDateTime.now()).toString());
    }
    

}
