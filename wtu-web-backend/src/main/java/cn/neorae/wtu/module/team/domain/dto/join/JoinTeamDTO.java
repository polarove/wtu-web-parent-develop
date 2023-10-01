package cn.neorae.wtu.module.team.domain.dto.join;

import lombok.Data;

@Data
public class JoinTeamDTO {


    private String channel;

    private Integer server;

    private String from;

    private String creatorUuid;

    private String uuid;

    private JoinTeamBuildDTO joinTeamBuildDTO;
}
