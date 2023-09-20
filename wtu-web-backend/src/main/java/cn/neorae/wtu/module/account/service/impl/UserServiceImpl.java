package cn.neorae.wtu.module.account.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.Enums;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.domain.dto.*;
import cn.neorae.wtu.module.account.domain.vo.UserVO;
import cn.neorae.wtu.module.account.mapper.UserMapper;
import cn.neorae.wtu.module.account.service.UserService;
import cn.neorae.wtu.module.mail.MailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-09-18 09:10:08
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Value("${server-addr.frontend}")
    private String frontAddr;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MailService mailService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseVO<UserVO> login(LoginDTO loginDTO) throws MessagingException {
        UserVO userVO = new UserVO();
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (BeanUtil.isEmpty(user)){
            return register(email, password, userVO);
        }
        if (!StrUtil.equals(password, user.getPassword())){
            return ResponseVO.failed(ResponseEnum.LOGIN_ERROR);
        }
        user.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        BeanUtil.copyProperties(user, userVO);
        this.baseMapper.updateById(user);
        StpUtil.login(user.getUuid());
        return ResponseVO.wrapData(userVO);
    }

    @Override
    public ResponseVO<ResponseEnum> verify(VerificationDTO verificationDTO) {
        String email = verificationDTO.getEmail();
        String uuid = verificationDTO.getUuid();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getUuid, uuid));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.wrapData(ResponseEnum.VERIFICATION_FAILED.getMessage(), ResponseEnum.VERIFICATION_FAILED.getCode(), null);
        }
        if (user.getVerified().equals(Enums.Polar.TRUE.getCode())){
            return ResponseVO.wrapData(ResponseEnum.VERIFICATION_DUPLICATED.getMessage(), ResponseEnum.VERIFICATION_DUPLICATED.getCode(), null);
        }
        user.setVerified(Enums.Polar.TRUE.getCode());
        this.baseMapper.updateById(user);
        return ResponseVO.wrapData(ResponseEnum.VERIFICATION_PASSED.getMessage(), ResponseEnum.VERIFICATION_PASSED.getCode(), null);
    }

    @Override
    public ResponseVO<String> getRecoverCode(String email) throws MessagingException {
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        String url = frontAddr + "/account/recover?email=" + email + "&uuid=" + user.getUuid();
        mailService.recoverAccount(email, "Warframe Team Up - 找回账户", url);
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(email, code,30, TimeUnit.MINUTES);
        return ResponseVO.wrapData(code);
    }

    @Override
    public ResponseVO<String> recoverAccount(RecoverAccountDTO recoverAccountDTO) {
        String email = recoverAccountDTO.getEmail();
        String uuid = recoverAccountDTO.getUuid();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getUuid, uuid));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        if (!StrUtil.equals(stringRedisTemplate.opsForValue().get(email), recoverAccountDTO.getCode())){
            return ResponseVO.failed(ResponseEnum.INCORPORATE_CODE);
        }
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<UserVO> saveMyName(SaveMyNameDTO saveMyNameDTO) {
        String email = saveMyNameDTO.getEmail();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        user.setName(saveMyNameDTO.getName());
        this.baseMapper.updateById(user);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return ResponseVO.wrapData(userVO);
    }
    @Override
    public ResponseVO<UserVO> changePassword(RevisePasswordDTO revisePasswordDTO) {
        String uuid = revisePasswordDTO.getUuid();
        String email = revisePasswordDTO.getEmail();
        String password = revisePasswordDTO.getPassword();
        String code = revisePasswordDTO.getCode();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUuid, uuid).eq(User::getEmail, email));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        if (StrUtil.equals(code, stringRedisTemplate.opsForValue().get(uuid))){
            return ResponseVO.failed(ResponseEnum.INCORPORATE_CODE);
        }
        user.setPassword(password);
        this.baseMapper.updateById(user);
        if (!StpUtil.isLogin(uuid)){
            StpUtil.login(uuid);
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        stringRedisTemplate.delete(email);
        return ResponseVO.wrapData(userVO);
    }

    @Override
    public ResponseVO<String> updateOnlineStatus(UpdateOnlineStatusDTO updateOnlineStatusDTO) {
        String uuid = updateOnlineStatusDTO.getUuid();
        Integer onlineStatus = updateOnlineStatusDTO.getOnlineStatus();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUuid, uuid));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        user.setOnlineStatus(onlineStatus);
        this.baseMapper.updateById(user);
        return ResponseVO.completed();
    }

    private ResponseVO<UserVO> register (String email, String password, UserVO userVO) throws MessagingException {
        User registered = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (BeanUtil.isNotEmpty(registered)){
            return ResponseVO.failed(ResponseEnum.USER_EXISTS);
        }
        String uuid = UUID.randomUUID().toString();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUuid(uuid);
        user.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        userMapper.register(user);
        userVO.setName("一般路过Tenno");
        userVO.setAvatar("https://www.neorae.cn/data/avatar/default/excalibur.png");
        userVO.setDescription("平平无奇的星际海盗罢了...");
        userVO.setUuid(uuid);
        userVO.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        String url = frontAddr + "/account/verify?uuid=" + uuid + "&email=" + email;
        mailService.verifyAccount(email, "Warframe Team Up - 账户验证", url);
        StpUtil.login(uuid);
        return ResponseVO.wrapData(userVO);
    }
}




