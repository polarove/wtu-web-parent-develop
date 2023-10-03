package cn.neorae.wtu.module.team.domain.bo;

import cn.neorae.wtu.module.account.domain.bo.UserBoosterBO;
import lombok.Data;

@Data
public class TeamUserBO {

    private String uuid;

    private String name;

    private String avatar;

    private Integer onlineStatus;

    private String accelerator;

    private UserBoosterBO booster;

}
