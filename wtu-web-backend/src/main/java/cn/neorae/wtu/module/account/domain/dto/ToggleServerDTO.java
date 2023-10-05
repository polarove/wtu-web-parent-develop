package cn.neorae.wtu.module.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToggleServerDTO {

    @Schema(description = "上一个服务器")
    @NotNull(message = "上一个服务器不能为空")
    private Integer previous;

    @Schema(description = "当前服务器")
    @NotNull(message = "当前服务器不能为空")
    private Integer current;
}
