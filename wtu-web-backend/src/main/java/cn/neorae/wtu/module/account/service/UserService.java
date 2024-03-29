package cn.neorae.wtu.module.account.service;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.domain.dto.*;
import cn.neorae.wtu.module.account.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-09-18 09:10:08
*/
public interface UserService extends IService<User> {

    ResponseVO<UserVO> login(LoginDTO loginDTO, HttpServletResponse response) throws MessagingException;

    ResponseVO<UserVO> loginByUUID(String uuid, HttpServletResponse response);

    ResponseVO<ResponseEnum> verify(VerificationDTO verificationDTO);

    ResponseVO<String> getRecoverCode(String email) throws MessagingException;

    ResponseVO<String> submitCode(RecoverAccountDTO recoverAccountDTO);

    ResponseVO<UserVO> changePassword(RevisePasswordDTO revisePasswordDTO, HttpServletResponse response);

    ResponseVO<UserVO> saveMyProfile(SaveMyProfileDTO saveMyProfileDTO);

    ResponseVO<String> updateOnlineStatus(Integer status);

    ResponseVO<String> updateUserBooster(UpdateUserBoosterDT0 updateUserBoosterDT0);

    ResponseVO<UserVO> getUserVOByUUID();

    ResponseVO<String> logout(String uuid, HttpServletResponse response);

    ResponseVO<UserVO> toggleServer(String uuid, ToggleServerDTO toggleServerDTO);

    ResponseVO<String> updateUserAccelerator(String name);

    ResponseVO<UserVO> togglePlatform(String loginIdAsString, String platform);

    ResponseVO<String> syncFissureSubscriptions(SyncFissureSubscriptionsDTO syncFissureSubscriptionsDTO);

    ResponseVO<SyncFissureSubscriptionsDTO> downloadFissureSubscriptions(String uuid);
}
