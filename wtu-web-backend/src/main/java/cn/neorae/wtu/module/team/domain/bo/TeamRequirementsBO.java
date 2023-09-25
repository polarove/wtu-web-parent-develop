package cn.neorae.wtu.module.team.domain.bo;


import lombok.Data;

@Data
public class TeamRequirementsBO {

    private Integer id;

    private String type;

    private String content;

    private Integer isDeleted;
}
