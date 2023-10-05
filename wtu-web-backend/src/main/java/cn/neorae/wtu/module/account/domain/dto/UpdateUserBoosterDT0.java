package cn.neorae.wtu.module.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserBoosterDT0 {

    @Schema(description = "经验值加成")
    @NotNull(message = "经验值加成不能为空")
    private Integer affinityBooster;

    @Schema(description = "现金加成")
    @NotNull(message = "现金加成不能为空")
    private Integer creditBooster;

    @Schema(description = "资源加成")
    @NotNull(message = "资源加成不能为空")
    private Integer resourceBooster;

    @Schema(description = "资源掉落加成")
    @NotNull(message = "资源掉落加成不能为空")
    private Integer resourceDropRateBooster;

    @Schema(description = "MOD掉落加成")
    @NotNull(message = "MOD掉落加成不能为空")
    private Integer modDropRateBooster;
}
