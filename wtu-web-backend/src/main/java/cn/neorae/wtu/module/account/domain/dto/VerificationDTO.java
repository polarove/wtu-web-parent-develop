package cn.neorae.wtu.module.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationDTO {

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;
}
