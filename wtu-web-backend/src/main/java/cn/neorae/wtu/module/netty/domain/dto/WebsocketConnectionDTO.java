package cn.neorae.wtu.module.netty.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebsocketConnectionDTO {

    private String route;

    private Integer action;

    private String uuid;

    private Integer server;
}
