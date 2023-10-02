package cn.neorae.wtu.module.team.domain.dto.join;

import lombok.Data;

@Data
public class JoinTeamDTO {


    private String receiver;

    private JoinTeamFromDTO from;

    private JoinTeamInstanceDTO team;

    private JoinTeamBuildDTO build;
}
