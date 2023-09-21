package cn.neorae.wtu.module.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserBoosterDT0 {

    @Schema(description = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;


    @Schema(description = "行为")
    @NotNull(message = "行为不能为空, 0取消，1启用")
    private Integer action;


    @Schema(description = "账户加成类型")
    @NotBlank(message = "账户类型不能为空")
    private String booster;
}
