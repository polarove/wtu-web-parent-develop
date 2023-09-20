package cn.neorae.wtu.module.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RevisePasswordDTO {

    @Schema(description = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;


    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;


    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
