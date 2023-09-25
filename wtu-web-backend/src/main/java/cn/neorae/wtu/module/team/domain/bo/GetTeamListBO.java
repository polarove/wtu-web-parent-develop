package cn.neorae.wtu.module.team.domain.bo;


import lombok.Data;

import java.util.List;

@Data
public class GetTeamListBO {


    private TeamBO team;

    private List<TeamMemberBO> memberList;

    private List<TeamRequirementsBO> requirementsList;

}

