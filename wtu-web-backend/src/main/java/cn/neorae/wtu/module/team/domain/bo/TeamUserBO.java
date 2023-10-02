package cn.neorae.wtu.module.team.domain.bo;

import lombok.Data;

@Data
public class TeamUserBO {

    private String uuid;

    private String name;

    private String avatar;

    private Integer onlineStatus;

    private Integer affinityBooster;

    private Integer creditBooster;

    private Integer resourceBooster;

    private Integer resourceDropRateBooster;

    private Integer modDropRateBooster;

    private String accelerator;

}
