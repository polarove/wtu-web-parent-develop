package cn.neorae.wtu.module.account.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.neorae.common.annotation.FreePass;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.account.domain.dto.*;
import cn.neorae.wtu.module.account.domain.vo.UserVO;
import cn.neorae.wtu.module.account.service.UserService;
import cn.neorae.wtu.common.util.CookieUtil;
import cn.neorae.wtu.common.util.Values;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "账户接口")
public class Account {

    @Resource
    private UserService userService;

    @Resource
    private Values values;

    @Operation(summary = "测试")
    @GetMapping("/hello")
    @FreePass
    public ResponseVO<String> hello() {
        return ResponseVO.wrapData("hello");
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
    public ResponseVO<UserVO> getUserVOByUUID() {
        return userService.getUserVOByUUID(StpUtil.getLoginIdAsString());
    }

    @Operation(summary = "退出")
    @GetMapping("/logout")
    public ResponseVO<String> logout(@RequestParam String uuid,HttpServletResponse response) {
        StpUtil.logout(uuid);
        CookieUtil.removeCookie(response, Values.Fingerprint, values.domain);
        CookieUtil.removeCookie(response, Values.Token, values.domain);
        return userService.logout(uuid, response);
    }

//    @Operation(summary = "是否登录")
//    @GetMapping("/isLogin")
//    public ResponseVO<Boolean> isLogin(@RequestParam String uuid) {
//        return ResponseVO.wrapData(StpUtil.isLogin(uuid));
//    }

    @Operation(summary = "变更用户名")
    @PostMapping("/saveMyName")
    public ResponseVO<UserVO> saveMyName(@Valid @RequestBody SaveMyNameDTO saveMyNameDTO ) {
        return userService.saveMyName(saveMyNameDTO);
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
}
