package cn.neorae.wtu.module.team.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateTeamDTO {

    private String title;

    private List<CreateTeamRequirementDTO> requirements;

    private List<CreateTeamMemberDTO> members;

    private Integer server;

    private String channel;
}
