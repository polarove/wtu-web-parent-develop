package cn.neorae.wtu.module.team.domain.bo;


import lombok.Data;

@Data
public class TeamMemberBO {

    private Integer id;

    private String focus;

    private Integer leader;

    private TeamWarframeBO warframe;

    private TeamUserBO user;

    private Integer isDeleted;
}
