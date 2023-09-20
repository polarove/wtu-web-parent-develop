package cn.neorae.wtu.module.account.service;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.domain.dto.*;
import cn.neorae.wtu.module.account.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.mail.MessagingException;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-09-18 09:10:08
*/
public interface UserService extends IService<User> {

    ResponseVO<UserVO> login(LoginDTO loginDTO) throws MessagingException;

    ResponseVO<ResponseEnum> verify(VerificationDTO verificationDTO);

    ResponseVO<String> getRecoverCode(String email) throws MessagingException;

    ResponseVO<String> recoverAccount(RecoverAccountDTO recoverAccountDTO);

    ResponseVO<UserVO> changePassword(RevisePasswordDTO revisePasswordDTO);

    ResponseVO<UserVO> saveMyName(SaveMyNameDTO saveMyNameDTO);

    ResponseVO<String> updateOnlineStatus(UpdateOnlineStatusDTO updateOnlineStatusDTO);
}
