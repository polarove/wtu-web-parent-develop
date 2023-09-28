package cn.neorae.wtu.module.account.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.Enums;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.common.util.CookieUtil;
import cn.neorae.wtu.common.util.UserUtil;
import cn.neorae.wtu.common.util.Values;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-09-18 09:10:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private Values values;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MailService mailService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseVO<UserVO> login(LoginDTO loginDTO, HttpServletResponse response) throws MessagingException {
        UserVO userVO = new UserVO();
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (BeanUtil.isEmpty(user)){
            return register(email, password, userVO, response);
        }
        if (!StrUtil.equals(password, user.getPassword())){
            return ResponseVO.failed(ResponseEnum.LOGIN_ERROR);
        }
        user.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        BeanUtil.copyProperties(user, userVO);
        List<String> boosterList = getBoosters(user);
        userVO.setBoosterList(boosterList);
        this.baseMapper.updateById(user);
        StpUtil.login(user.getUuid());
        CookieUtil.setCookie(response, Values.Fingerprint, user.getUuid(), Values.CookieExpiry, values.domain);
        return ResponseVO.wrapData(userVO);
    }

    @Override
    public List<String> getBoosters(User user) {
        // 最无语的一集
        List<String> boosterList = new ArrayList<>();
        if (BeanUtil.isEmpty(user)){
            return boosterList;
        }
        if (user.getAffinityBooster().equals(Enums.Polar.TRUE.getCode())){
            boosterList.add(Enums.Booster.AFFINITY.getType());
        }
        if (user.getCreditBooster().equals(Enums.Polar.TRUE.getCode())){
            boosterList.add(Enums.Booster.CREDIT.getType());
        }
        if (user.getResourceBooster().equals(Enums.Polar.TRUE.getCode())){
            boosterList.add(Enums.Booster.RESOURCE.getType());
        }
        if (user.getModDropRateBooster().equals(Enums.Polar.TRUE.getCode())){
            boosterList.add(Enums.Booster.MOD_DROP_CHANCE.getType());
        }
        if (user.getResourceDropRateBooster().equals(Enums.Polar.TRUE.getCode())){
            boosterList.add(Enums.Booster.RESOURCE_DROP_CHANCE.getType());
        }
        return boosterList;
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
        String url = values.frontAddr + "/account/recover?email=" + email + "&uuid=" + user.getUuid();
        mailService.recoverAccount(email, "Warframe Team Up - 找回账户", url);
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(email, code,30, TimeUnit.MINUTES);
        return ResponseVO.wrapData(code);
    }

    @Override
    public ResponseVO<String> submitCode(RecoverAccountDTO recoverAccountDTO) {
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
    public ResponseVO<UserVO> changePassword(RevisePasswordDTO revisePasswordDTO, HttpServletResponse response) {
        String uuid = revisePasswordDTO.getUuid();
        String email = revisePasswordDTO.getEmail();
        String password = revisePasswordDTO.getPassword();
        String code = revisePasswordDTO.getCode();

        // 验证验证码
        if (!StrUtil.equals(code, stringRedisTemplate.opsForValue().get(email))){
            return ResponseVO.failed(ResponseEnum.INCORPORATE_CODE);
        }

        // 修改密码
        User user = this.baseMapper.getUserByUUID(uuid, Enums.Polar.FALSE.getCode());
        user.setPassword(password);
        this.baseMapper.updateById(user);

        // 刷新登录状态
        if (!StpUtil.isLogin(uuid)){
            StpUtil.login(uuid);
        }

        // 设置cookie
        CookieUtil.setCookie(response, Values.Fingerprint, uuid, Values.CookieExpiry, values.domain);

        // 属性拷贝
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);

        // 设置加成列表
        List<String> boosterList = getBoosters(user);
        userVO.setBoosterList(boosterList);

        stringRedisTemplate.delete(email);
        return ResponseVO.wrapData(userVO);
    }


    @Override
    public ResponseVO<UserVO> getUserVOByUUID(HttpServletRequest request) {
        String uuid = CookieUtil.getUUID(request, Values.Fingerprint);
        if (StrUtil.isBlank(uuid)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        if (!StpUtil.isLogin(uuid)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_LOGIN);
        }
        User user = UserUtil.getUserByUuid(uuid);
        return ResponseVO.wrapData(parseData(user));
    }

    @Override
    public ResponseVO<String> logout(String uuid, HttpServletResponse response) {
        User user = UserUtil.getUserByUuid(uuid);
        user.setOnlineStatus(Enums.OnlineStatus.OFFLINE.getCode());
        this.baseMapper.updateById(user);
        CookieUtil.removeCookie(response, Values.Fingerprint, values.domain);
        CookieUtil.removeCookie(response, Values.Token, values.domain);
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<UserVO> saveMyProfile(SaveMyProfileDTO saveMyProfileDTO) {
        String uuid = saveMyProfileDTO.getUuid();
        User user = UserUtil.getUserByUuid(uuid);
        BeanUtil.copyProperties(saveMyProfileDTO, user);
        this.baseMapper.updateById(user);
        return ResponseVO.wrapData(parseData(user));
    }

    @Override
    public ResponseVO<String> updateOnlineStatus(UpdateOnlineStatusDTO updateOnlineStatusDTO) {
        String uuid = updateOnlineStatusDTO.getUuid();
        Integer onlineStatus = updateOnlineStatusDTO.getOnlineStatus();
        User user = UserUtil.getUserByUuid(uuid);
        user.setOnlineStatus(onlineStatus);
        this.baseMapper.updateById(user);
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<String> updateUserBooster(UpdateUserBoosterDT0 updateUserBoosterDT0) {
        String uuid = updateUserBoosterDT0.getUuid();
        Integer action = updateUserBoosterDT0.getAction();
        String booster = updateUserBoosterDT0.getBooster();
        User user = UserUtil.getUserByUuid(uuid);
        if (!setBooster(user, booster, action)){
            return ResponseVO.failed(ResponseEnum.INVALID_BOOSTER);
        }
        this.baseMapper.updateById(user);
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<UserVO> toggleServer(String uuid, Integer serverType) {
        User user = UserUtil.getUserByUuid(uuid);
        if(BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        user.setServer(serverType);
        this.baseMapper.updateById(user);
        return ResponseVO.wrapData(parseData(user));
    }

    private boolean setBooster(User user, String booster, Integer action){
        if(StrUtil.equals(booster, Enums.Booster.AFFINITY.getType())){
            user.setAffinityBooster(action);
        }else if (StrUtil.equals(booster, Enums.Booster.CREDIT.getType())) {
            user.setCreditBooster(action);
        }else if (StrUtil.equals(booster, Enums.Booster.RESOURCE.getType())) {
            user.setResourceBooster(action);
        }else if (StrUtil.equals(booster, Enums.Booster.MOD_DROP_CHANCE.getType())) {
            user.setModDropRateBooster(action);
        }else if (StrUtil.equals(booster, Enums.Booster.RESOURCE_DROP_CHANCE.getType())) {
            user.setResourceDropRateBooster(action);
        }else{
            return false;
        }
        return true;
    }

    private ResponseVO<UserVO> register (String email, String password, UserVO userVO, HttpServletResponse response) throws MessagingException {
        User registered = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (BeanUtil.isNotEmpty(registered)){
            return ResponseVO.failed(ResponseEnum.USER_EXISTS);
        }
        String uuid = UUID.randomUUID().toString();

        // 注册
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUuid(uuid);
        user.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        userMapper.register(user);

        // 属性拷贝
        userVO.setName("一般路过Tenno");
        userVO.setAvatar("https://www.neorae.cn/data/avatar/default/excalibur.png");
        userVO.setDescription("平平无奇的星际海盗罢了...");
        userVO.setUuid(uuid);
        userVO.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        userVO.setServer(Enums.Server.INTERNATIONAL.getCode());
        userVO.setLevel(0);

        // 设置加成列表
        userVO.setBoosterList(new ArrayList<>());

        // 发送验证邮件
        String url = values.frontAddr + "/account/verify?uuid=" + uuid + "&email=" + email;
        mailService.verifyAccount(email, "Warframe Team Up - 账户验证", url);

        // 刷新登录状态
        StpUtil.login(uuid);

        // 设置cookie
        CookieUtil.setCookie(response, Values.Fingerprint, uuid, Values.CookieExpiry, values.domain);

        return ResponseVO.wrapData(userVO);
    }

    private UserVO parseData (User user) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        List<String> boosterList = getBoosters(user);
        userVO.setBoosterList(boosterList);
        return userVO;
    }
}




