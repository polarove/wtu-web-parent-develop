package cn.neorae.wtu.module.team.domain.dto.join;

import cn.neorae.wtu.module.team.domain.bo.TeamWarframeBO;
import lombok.Data;

@Data
public class JoinTeamBuildDTO {

    private String focus;

    private TeamWarframeBO warframe;
}
