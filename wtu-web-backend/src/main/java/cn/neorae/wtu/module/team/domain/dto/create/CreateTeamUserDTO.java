package cn.neorae.wtu.module.team.domain.dto.create;

import lombok.Data;

@Data
public class CreateTeamUserDTO {

    private String uuid;

    private String name;

    private String level;

    private String avatar;
}
