package cn.neorae.wtu.module.team.domain.bo;


import lombok.Data;

import java.util.List;

@Data
public class TeamMemberBO {

    private Integer id;

    private String focus;

    private Integer leader;

    private TeamWarframeBO warframe;

    private Integer occupied;

    private UserBO user;

    private Integer isDeleted;
}
