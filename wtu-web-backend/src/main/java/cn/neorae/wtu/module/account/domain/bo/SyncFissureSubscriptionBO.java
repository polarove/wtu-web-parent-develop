package cn.neorae.wtu.module.account.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class SyncFissureSubscriptionBO {

    private String channel;

    private List<FissureBO> mission;
}
