package cn.neorae.wtu.module.team.domain.dto.create;

import lombok.Data;

@Data
public class CreateTeamMemberDTO {

    private CreateTeamUserDTO user;

    private CreateTeamWarframeDTO warframe;

    private String focus;

    private Integer leader;

    private Integer occupied;

}
