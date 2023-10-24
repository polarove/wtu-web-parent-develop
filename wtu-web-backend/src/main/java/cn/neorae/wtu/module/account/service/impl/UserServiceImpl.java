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
import cn.neorae.wtu.module.account.domain.bo.UserBoosterBO;
import cn.neorae.wtu.module.account.domain.dto.*;
import cn.neorae.wtu.module.account.domain.vo.UserVO;
import cn.neorae.wtu.module.account.mapper.UserMapper;
import cn.neorae.wtu.module.account.service.UserService;
import cn.neorae.wtu.module.mail.MailService;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.UserException;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import cn.neorae.wtu.module.team.service.TeamService;
import cn.neorae.wtu.module.team.service.impl.TeamThreadTaskServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Service实现
* @Date 2023-09-18 09:10:08
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private Values values;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MailService mailService;

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private TeamThreadTaskServiceImpl teamThreadTaskService;

    @Resource
    private TeamService teamService;

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
        return getUserVOResponseVO(response, user);
    }
    @Override
    public ResponseVO<UserVO> loginByUUID(String uuid, HttpServletResponse response) {
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUuid, uuid));
        if (BeanUtil.isEmpty(user)){
            throw new UserException(ResponseEnum.USER_NOT_FOUND);
        }
        return getUserVOResponseVO(response, user);
    }

    private ResponseVO<UserVO> getUserVOResponseVO(HttpServletResponse response, User user) {
        user.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        this.baseMapper.updateById(user);
        StpUtil.login(user.getUuid());
        CookieUtil.setCookie(response, Values.Fingerprint, user.getUuid(), Values.CookieExpiry, values.domain);
        return ResponseVO.wrapData(parseUserVO(user));
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
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getUuid, uuid));
        if (BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        user.setPassword(password);
        this.baseMapper.updateById(user);

        // 刷新登录状态
        if (!StpUtil.isLogin(uuid)){
            StpUtil.login(uuid);
        }

        // 设置cookie
        CookieUtil.setCookie(response, Values.Fingerprint, uuid, Values.CookieExpiry, values.domain);
        stringRedisTemplate.delete(email);
        return ResponseVO.wrapData(parseUserVO(user));
    }


    @Override
    public ResponseVO<UserVO> getUserVOByUUID() {
        String uuid = StpUtil.getLoginIdAsString();
        User user = UserUtil.getUserByUuid(uuid);
        return ResponseVO.wrapData(parseUserVO(user));
    }

    @Override
    public ResponseVO<String> logout(String uuid, HttpServletResponse response) {
        User user = UserUtil.getUserByUuid(uuid);
        StpUtil.getSession().set(uuid, user);
        user.setOnlineStatus(Enums.OnlineStatus.OFFLINE.getCode());
        this.baseMapper.updateById(user);
        StpUtil.logout(uuid);
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
        return ResponseVO.wrapData(parseUserVO(user));
    }

    @Override
    // todo: wss广播通知
    public ResponseVO<String> updateOnlineStatus(Integer status) {
        String uuid = StpUtil.getLoginIdAsString();
        User user = UserUtil.getUserByUuid(uuid);
        user.setOnlineStatus(status);
        this.baseMapper.updateById(user);
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<String> updateUserBooster(UpdateUserBoosterDT0 updateUserBoosterDT0) {
        String uuid = StpUtil.getLoginIdAsString();
        User user = UserUtil.getUserByUuid(uuid);
        BeanUtil.copyProperties(updateUserBoosterDT0, user);
        this.baseMapper.updateById(user);
        return ResponseVO.completed();
    }

    @Override
    // todo: wss广播通知
    public ResponseVO<String> updateUserAccelerator(String name) {
        String uuid = StpUtil.getLoginIdAsString();
        User user = UserUtil.getUserByUuid(uuid);
        user.setAccelerator(name);
        this.baseMapper.updateById(user);
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<UserVO> toggleServer(String uuid, ToggleServerDTO toggleServerDTO) {
        User user = UserUtil.getUserByUuid(uuid);
        if(BeanUtil.isEmpty(user)){
            return ResponseVO.failed(ResponseEnum.USER_NOT_FOUND);
        }
        Integer current = toggleServerDTO.getCurrent();
        Integer previous = toggleServerDTO.getPrevious();
        user.setServer(current);
        this.baseMapper.updateById(user);
        List<Team> teams = teamMapper
                .selectList(
                        new LambdaQueryWrapper<Team>()
                                .eq(Team::getCreatorUuid, uuid))
                .parallelStream()
                .peek(team -> {
                    if (team.getServer().equals(previous)){
                        teamThreadTaskService.setTeamStatus(team, NettyServerEnum.TeamStatusEnum.PRIVATE.getType());
                    }else {
                        teamThreadTaskService.setTeamStatus(team, NettyServerEnum.TeamStatusEnum.PUBLIC.getType());
                    }
                })
                .toList();
        teamService.updateBatchById(teams);
        return ResponseVO.wrapData(parseUserVO(user));
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
        this.baseMapper.insert(user);

        // 属性拷贝
        userVO.setName("一般路过Tenno");
        userVO.setAvatar("https://www.neorae.cn/data/avatar/default/excalibur.png");
        userVO.setDescription("平平无奇的星际海盗罢了...");
        userVO.setUuid(uuid);
        userVO.setOnlineStatus(Enums.OnlineStatus.ONLINE.getCode());
        userVO.setServer(NettyServerEnum.GameServerEnum.EN.getType());
        userVO.setLevel(0);

        UserBoosterBO userBoosterBO = new UserBoosterBO();
        userBoosterBO.setAffinityBooster(Enums.Polar.FALSE.getCode());
        userBoosterBO.setCreditBooster(Enums.Polar.FALSE.getCode());
        userBoosterBO.setResourceBooster(Enums.Polar.FALSE.getCode());
        userBoosterBO.setModDropRateBooster(Enums.Polar.FALSE.getCode());
        userBoosterBO.setResourceDropRateBooster(Enums.Polar.FALSE.getCode());
        userVO.setBooster(userBoosterBO);

        // 发送验证邮件
        String url = values.frontAddr + "/account/verify?uuid=" + uuid + "&email=" + email;
        mailService.verifyAccount(email, "Warframe Team Up - 账户验证", url);

        // 刷新登录状态
        StpUtil.login(uuid);

        // 设置cookie
        CookieUtil.setCookie(response, Values.Fingerprint, uuid, Values.CookieExpiry, values.domain);

        return ResponseVO.wrapData(userVO);
    }

    private UserVO parseUserVO (User user) {
        UserVO userVO = new UserVO();
        UserBoosterBO userBoosterBO = new UserBoosterBO();
        BeanUtil.copyProperties(user, userBoosterBO);
        BeanUtil.copyProperties(user, userVO);
        userVO.setBooster(userBoosterBO);
        return userVO;
    }
}




