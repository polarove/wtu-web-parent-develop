package cn.neorae.wtu.module.account.domain.vo;

import cn.neorae.wtu.module.team.domain.bo.TeamUserBO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends TeamUserBO {

    private String description;

    private Integer server;

    private Integer level;

}
