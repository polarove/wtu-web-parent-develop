package cn.neorae.wtu.module.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveMyProfileDTO {

    @Schema(description = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;

}
