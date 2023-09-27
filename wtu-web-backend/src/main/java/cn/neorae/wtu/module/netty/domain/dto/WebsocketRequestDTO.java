package cn.neorae.wtu.module.netty.domain.dto;

import lombok.Data;

@Data
public class WebsocketRequestDTO {


    private String from;

    private String receiver;

    private String data;

    private Integer action;
}
