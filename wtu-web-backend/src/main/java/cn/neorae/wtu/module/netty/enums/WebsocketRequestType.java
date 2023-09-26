package cn.neorae.wtu.module.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebsocketRequestType {

    
    INSERT(1),

    UPDATE(2),

    DELETE(3),

    SELECT(4),

    CONNECT(5),

    DISCONNECT(6),

    NOT_SUPPORTED(0);

    private Integer type;

    public static WebsocketRequestType match (Integer type) {
        for (WebsocketRequestType websocketRequestType : WebsocketRequestType.values()) {
            if (websocketRequestType.getType().equals(type)) {
                return websocketRequestType;
            }
        }
        return NOT_SUPPORTED;
    }
}
