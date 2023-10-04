package cn.neorae.wtu.module.team.domain.dto.join;

import lombok.Data;

@Data
public class ApplicationDTO {

    private String receiver;

    private String status;

    private Integer isDeleted;

    private ApplicationFromDTO from;

    private ApplicationTeamDTO team;

    private ApplicationBuildDTO build;
}
