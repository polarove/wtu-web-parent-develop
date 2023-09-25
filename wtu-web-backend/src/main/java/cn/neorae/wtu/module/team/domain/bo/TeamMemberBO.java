package cn.neorae.wtu.module.team.domain.bo;


import lombok.Data;

@Data
public class TeamMemberBO {

    private Integer id;

    private String focus;

    private Integer role;

    private TeamWarframeBO teamWarframeBO;

    private Integer isDeleted;
}
