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
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "账户接口")
public class Account {

    @Resource
    private UserService userService;

    @Operation(summary = "测试")
    @GetMapping("/hello")
    @FreePass
    public ResponseVO<String> hello() {
        return ResponseVO.wrapData("hello");
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    @FreePass
    public ResponseVO<UserVO> login(@Valid @RequestBody LoginDTO loginDTO) throws MessagingException {
        return userService.login(loginDTO);
    }

    @Operation(summary = "验证")
    @PostMapping("/verify")
    @FreePass
    public ResponseVO<ResponseEnum> verify(@RequestBody VerificationDTO verificationDTO) {
        return userService.verify(verificationDTO);
    }

    @Operation(summary = "退出")
    @GetMapping("/logout")
    public ResponseVO<String> logout(@RequestParam String uuid) {
        StpUtil.logout(uuid);
        return ResponseVO.wrapData("已退出登录");
    }

//    @Operation(summary = "是否登录")
//    @GetMapping("/isLogin")
//    public ResponseVO<Boolean> isLogin(@RequestParam String uuid) {
//        return ResponseVO.wrapData(StpUtil.isLogin(uuid));
//    }

    @Operation(summary = "获取找回账号的邮件")
    @GetMapping("/getRecoverCode")
    @FreePass
    public ResponseVO<String> getRecoverCode(@RequestParam String email) throws MessagingException {
        return userService.getRecoverCode(email);
    }

    @Operation(summary = "找回账号")
    @PostMapping("/recoverAccount")
    @FreePass
    public ResponseVO<String> recoverAccount(@RequestBody RecoverAccountDTO recoverAccountDTO)  {
        return userService.recoverAccount(recoverAccountDTO);
    }

    @Operation(summary = "变更密码")
    @PostMapping("/changePassword")
    @FreePass
    public ResponseVO<UserVO> changePassword(@RequestBody RevisePasswordDTO revisePasswordDTO ) {
        return userService.changePassword(revisePasswordDTO);
    }


    @Operation(summary = "变更用户名")
    @PostMapping("/saveMyName")
    @FreePass
    public ResponseVO<UserVO> saveMyName(@RequestBody SaveMyNameDTO saveMyNameDTO ) {
        return userService.saveMyName(saveMyNameDTO);
    }

    @Operation(summary = "变更在线状态")
    @PostMapping("/updateOnlineStatus")
    @FreePass
    public ResponseVO<String> updateOnlineStatus(@RequestBody UpdateOnlineStatusDTO updateOnlineStatusDTO ) {
        return userService.updateOnlineStatus(updateOnlineStatusDTO);
    }
}
