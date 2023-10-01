package cn.neorae.wtu.module.account.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.neorae.common.annotation.FreePass;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.account.domain.dto.*;
import cn.neorae.wtu.module.account.domain.vo.UserVO;
import cn.neorae.wtu.module.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "账户接口")
@Slf4j
public class AccountController {

    @Value("${spring.profiles.active}")
    private String env;

    @Resource
    private UserService userService;


    @Operation(summary = "测试")
    @GetMapping("/hello")
    @FreePass
    public ResponseVO<String> hello() {
        return ResponseVO.wrapData(env);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    @FreePass
    public ResponseVO<UserVO> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) throws MessagingException {
        return userService.login(loginDTO, response);
    }

    @Operation(summary = "验证")
    @PostMapping("/verify")
    @FreePass
    public ResponseVO<ResponseEnum> verify(@Valid @RequestBody VerificationDTO verificationDTO) {
        return userService.verify(verificationDTO);
    }

    @Operation(summary = "获取找回账号的邮件")
    @GetMapping("/getRecoverCode")
    @FreePass
    public ResponseVO<String> getRecoverCode(@Valid @RequestParam String email) throws MessagingException {
        return userService.getRecoverCode(email);
    }

    @Operation(summary = "提交找回账号所需的验证码")
    @PostMapping("/submitCode")
    @FreePass
    public ResponseVO<String> submitCode(@Valid @RequestBody RecoverAccountDTO recoverAccountDTO)  {
        return userService.submitCode(recoverAccountDTO);
    }

    @Operation(summary = "变更密码")
    @PostMapping("/changePassword")
    @FreePass
    public ResponseVO<UserVO> changePassword(@Valid @RequestBody RevisePasswordDTO revisePasswordDTO, HttpServletResponse response) {
        return userService.changePassword(revisePasswordDTO, response);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserVOByUUID")
    public ResponseVO<UserVO> getUserVOByUUID(HttpServletRequest request) {
        return userService.getUserVOByUUID(request);
    }

    @Operation(summary = "退出")
    @GetMapping("/logout")
    public ResponseVO<String> logout(@RequestParam String uuid,HttpServletResponse response) {
        return userService.logout(uuid, response);
    }

    @Operation(summary = "变更用户名")
    @PostMapping("/saveMyProfile")
    public ResponseVO<UserVO> saveMyProfile(@Valid @RequestBody SaveMyProfileDTO saveMyProfileDTO) {
        return userService.saveMyProfile(saveMyProfileDTO);
    }

    @Operation(summary = "变更在线状态")
    @PostMapping("/updateOnlineStatus")
    public ResponseVO<String> updateOnlineStatus(@Valid @RequestBody UpdateOnlineStatusDTO updateOnlineStatusDTO ) {
        return userService.updateOnlineStatus(updateOnlineStatusDTO);
    }

    @Operation(summary = "变更账户加成状态")
    @PostMapping("/updateUserBooster")
    public ResponseVO<String> updateUserBooster(@Valid @RequestBody UpdateUserBoosterDT0 updateUserBoosterDT0 ) {
        return userService.updateUserBooster(updateUserBoosterDT0);
    }

    @Operation(summary = "切换服务器")
    @GetMapping("/toggleServer")
    public ResponseVO<UserVO> toggleServer(@RequestParam Integer serverType) {
        return userService.toggleServer(StpUtil.getLoginIdAsString(), serverType);
    }
}
