package cn.neorae.wtu.module.netty.team.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebsocketRequestDTO {

    private Integer channelId;

    private Integer action;

    private String uuid;

    private Object data;
}
