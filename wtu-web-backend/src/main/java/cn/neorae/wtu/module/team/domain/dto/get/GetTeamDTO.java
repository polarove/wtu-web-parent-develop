package cn.neorae.wtu.module.team.domain.dto.get;

import lombok.Data;

@Data
public class GetTeamDTO {

    private Integer page;

    private Integer size;

    private String channel;

    private Integer server;

    private String uuid;
}
