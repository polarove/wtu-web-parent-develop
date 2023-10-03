package cn.neorae.wtu.module.account.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserBoosterDT0 {

    @NotNull(message = "经验值加成不能为空")
    private Integer affinityBooster;

    @NotNull(message = "现金加成不能为空")
    private Integer creditBooster;

    @NotNull(message = "资源加成不能为空")
    private Integer resourceBooster;

    @NotNull(message = "资源掉落加成不能为空")
    private Integer resourceDropRateBooster;

    @NotNull(message = "MOD掉落加成不能为空")
    private Integer modDropRateBooster;
}
