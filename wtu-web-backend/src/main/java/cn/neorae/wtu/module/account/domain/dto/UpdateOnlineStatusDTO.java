package cn.neorae.wtu.module.account.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOnlineStatusDTO {


    @Schema(description = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @Schema(description = "在线状态")
    @NotNull(message = "在线状态不能为空")
    private Integer onlineStatus;
}
