package cn.neorae.wtu.module.team.domain.dto.join;

import lombok.Data;

@Data
public class JoinTeamInstanceDTO {

    private String uuid;

    private Integer server;

    private String channel;

    private String creatorUuid;

    private String title;
}
