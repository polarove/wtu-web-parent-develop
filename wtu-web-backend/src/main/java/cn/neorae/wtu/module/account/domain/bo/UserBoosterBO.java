package cn.neorae.wtu.module.account.domain.bo;

import lombok.Data;

@Data
public class UserBoosterBO {

    private Integer affinityBooster;

    private Integer creditBooster;

    private Integer resourceBooster;

    private Integer resourceDropRateBooster;

    private Integer modDropRateBooster;
}
