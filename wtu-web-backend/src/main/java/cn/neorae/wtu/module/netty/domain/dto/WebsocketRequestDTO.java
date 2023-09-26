package cn.neorae.wtu.module.netty.domain.dto;

import lombok.Data;

@Data
public class WebsocketRequestDTO {

    private Integer channelId;

    private Integer type;

    private String uuid;

    private Object data;
}
