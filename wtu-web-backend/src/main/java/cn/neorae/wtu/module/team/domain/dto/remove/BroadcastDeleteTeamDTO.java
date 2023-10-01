package cn.neorae.wtu.module.team.domain.dto.remove;

import lombok.Data;

@Data
public class BroadcastDeleteTeamDTO {


    private String route;

    private Integer teamId;

    private Integer server;
}
