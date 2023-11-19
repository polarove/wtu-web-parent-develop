package cn.neorae.wtu.module.account.domain.dto;

import cn.neorae.wtu.module.account.domain.bo.SyncFissureSubscriptionBO;
import lombok.Data;

import java.util.List;

@Data
public class SyncFissureSubscriptionsDTO {

    private List<SyncFissureSubscriptionBO> subscriptionList;

    private List<String> notifyHistory;
}
