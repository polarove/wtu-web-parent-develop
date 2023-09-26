package cn.neorae.wtu.module.team.domain.vo;


import cn.neorae.wtu.module.team.domain.bo.TeamBO;
import cn.neorae.wtu.module.team.domain.bo.TeamMemberBO;
import cn.neorae.wtu.module.team.domain.bo.TeamRequirementsBO;
import lombok.Data;

import java.util.List;

@Data
public class TeamVO {


    private TeamBO team;

    private List<TeamMemberBO> members;

    private List<TeamRequirementsBO> requirements;

}

