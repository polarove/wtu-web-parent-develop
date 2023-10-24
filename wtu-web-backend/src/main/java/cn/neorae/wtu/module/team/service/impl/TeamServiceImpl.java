package cn.neorae.wtu.module.team.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.Enums;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.common.exception.TeamException;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.util.ChannelUtil;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastDeleteTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.dto.create.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.get.GetTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.join.ApplicationDTO;
import cn.neorae.wtu.module.team.domain.dto.toggle.ToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.service.TeamService;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.netty.channel.group.ChannelGroup;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2023-09-25 08:50:11
*/
@Service
@Slf4j
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService {


    @Resource
    private TeamRequirementService teamRequirementService;

    @Resource
    private TeamRequirementMapper teamRequirementMapper;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Resource
    private TeamThreadTaskServiceImpl teamThreadTaskService;


    @Resource
    private TeamMapper teamMapper;

    @Resource
    private RedissonClient redissonClient;

    private static final String TEAM_APPLICATION_PREFIX = "team:application:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<Integer> createTeam(CreateTeamDTO createTeamDTO) {

        Team team = new Team();
        team.setTitle(createTeamDTO.getTitle());
        team.setServer(createTeamDTO.getServer());
        team.setCreatorUuid(StpUtil.getLoginIdAsString());
        team.setChannel(createTeamDTO.getChannel());
        team.setUuid(UUID.randomUUID().toString());
        team.setStatus(Enums.Polar.TRUE.getCode());
        team.setIsDeleted(Enums.Polar.FALSE.getCode());
        team.setIsPublic(createTeamDTO.getIsPublic());

        // 保存队伍
        if (BeanUtil.isNotEmpty(team)){
            this.baseMapper.insert(team);
        }

        // 装配队伍需求
        List<TeamRequirement> requirementList = createTeamDTO
                .getRequirements()
                .parallelStream()
                .map(requirement -> teamThreadTaskService.handleTeamRequirements(requirement, team.getId()).join())
                .toList();

        // 装配队伍成员信息
        List<TeamMember> memberList = createTeamDTO
                .getMembers()
                .parallelStream()
                .map(member -> teamThreadTaskService.handleTeamMembers(member,team.getId()).join())
                .toList();

        // 保存队伍需求和队伍成员信息
        if (ArrayUtil.isNotEmpty(requirementList)){
            teamRequirementService.saveBatch(requirementList);
        }
        if (ArrayUtil.isNotEmpty(memberList)){
            teamMemberService.saveBatch(memberList);
        }else{
            throw new TeamException(ResponseEnum.TEAM_MEMBER_NOT_SATISFIED);
        }

        return ResponseVO.wrapData(team.getId());
    }

    @Override
    public ResponseVO<String> broadcastTeam(BroadcastTeamDTO broadcastTeamDTO) {
        Integer server = broadcastTeamDTO.getTeam().getServer();
        String route = broadcastTeamDTO.getTeam().getChannel();
        switch (NettyServerEnum.GameServerEnum.match(server)) {
            case EN -> ChannelUtil.getEnChannelGroupByRoute(route).writeAndFlush(WssResponseVO.ADD_TEAM(JSON.toJSONString(broadcastTeamDTO)));
            case CN -> ChannelUtil.getCnChannelGroupByRoute(route).writeAndFlush(WssResponseVO.ADD_TEAM(JSON.toJSONString(broadcastTeamDTO)));
            default -> throw new TeamException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        return ResponseVO.completed();
    }

    @Override
    @Transactional
    public ResponseVO<String> removeTeamById(Integer teamId) {
        Team team = this.getOne(new LambdaQueryWrapper<Team>().eq(Team::getId, teamId));
        if (!StrUtil.equals(team.getCreatorUuid(), StpUtil.getLoginIdAsString())){
            throw new TeamException(ResponseEnum.TEAM_NOT_FOUND);
        }
        List<Integer> requirementIdList = teamRequirementMapper
                .selectList(new LambdaQueryWrapper<TeamRequirement>().eq(TeamRequirement::getTeamId, teamId))
                .parallelStream()
                .map(TeamRequirement::getId).toList();

        List<Integer> memberIdList = teamMemberMapper
                .selectList(new LambdaQueryWrapper<TeamMember>().eq(TeamMember::getTeamId, teamId))
                .parallelStream()
                .map(TeamMember::getId).toList();

        if (BeanUtil.isNotEmpty(team)){
            removeById(team);
        } else {
            throw new TeamException(ResponseEnum.TEAM_NOT_FOUND);
        }
        if (ArrayUtil.isNotEmpty(memberIdList)){
            teamMemberService.removeBatchByIds(memberIdList);
        } else {
            throw new TeamException(ResponseEnum.TEAM_MEMBER_NOT_FOUND);
        }
        if (ArrayUtil.isNotEmpty(requirementIdList)){
            teamRequirementService.removeBatchByIds(requirementIdList);
        }
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<String> broadcastDeleteTeam(BroadcastDeleteTeamDTO broadcastDeleteTeamDTO) {
        Integer server = broadcastDeleteTeamDTO.getServer();
        String route = broadcastDeleteTeamDTO.getRoute();
        Integer teamId = broadcastDeleteTeamDTO.getTeamId();
        switch (NettyServerEnum.GameServerEnum.match(server)) {
            case EN -> ChannelUtil.getEnChannelGroupByRoute(route).writeAndFlush(WssResponseVO.REMOVE_TEAM(JSON.toJSONString(teamId)));
            case CN -> ChannelUtil.getCnChannelGroupByRoute(route).writeAndFlush(WssResponseVO.REMOVE_TEAM(JSON.toJSONString(teamId)));
            default -> throw new TeamException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        return ResponseVO.completed();
    }


    @Override
    public ResponseVO<String> broadcastToggleTeamStatus(BroadcastToggleTeamStatusDTO broadcastToggleTeamStatusDTO) {
        Integer server = broadcastToggleTeamStatusDTO.getTeam().getServer();
        String route = broadcastToggleTeamStatusDTO.getTeam().getChannel();
        switch (NettyServerEnum.GameServerEnum.match(server)) {
            case EN -> ChannelUtil.getEnChannelGroupByRoute(route).writeAndFlush(WssResponseVO.TOGGLE_STATUS(JSON.toJSONString(broadcastToggleTeamStatusDTO)));
            case CN -> ChannelUtil.getCnChannelGroupByRoute(route).writeAndFlush(WssResponseVO.TOGGLE_STATUS(JSON.toJSONString(broadcastToggleTeamStatusDTO)));
            default -> throw new TeamException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<TeamVO> getTeamById(Integer teamId){
        Team team = this.baseMapper.selectOne(new LambdaQueryWrapper<Team>().eq(Team::getId, teamId));
        if (BeanUtil.isEmpty(team)){
            return ResponseVO.failed(ResponseEnum.TEAM_NOT_FOUND);
        }
        return teamThreadTaskService.getTeamVO(team).thenApply(ResponseVO::wrapData).join();
    }

    @Override
    public ResponseVO<IPage<TeamVO>> getTeamList(GetTeamDTO getTeamDTO){
        long cur = System.currentTimeMillis();
        IPage<Team> teamPage = teamMapper.selectJoinPage(
                new Page<>(getTeamDTO.getPage(), getTeamDTO.getSize()),
                Team.class,
                new MPJLambdaWrapper<Team>()
                        .selectAll(Team.class) // 主表
                        .select(User::getUuid) // 连表查询的字段
                        .leftJoin(User.class, User::getUuid, Team::getCreatorUuid) // 连表条件
                        .eq(StrUtil.isNotBlank(getTeamDTO.getChannel()), Team::getChannel, getTeamDTO.getChannel()) // 查询指定频道的组队信息
                        .eq(Team::getServer, getTeamDTO.getServer()) // 查询指定服务器的组队信息
                        .eq(StrUtil.isNotBlank(getTeamDTO.getUuid()), User::getUuid, getTeamDTO.getUuid()) // 若uuid不为空，查询该用户发布的组队信息
                        .eq(StrUtil.isBlank(getTeamDTO.getUuid()), Team::getStatus, Enums.Polar.TRUE.getCode()) // 若uuid为空，则只查询公开的组队
                        .ne(StrUtil.isBlank(getTeamDTO.getUuid()), User::getOnlineStatus, Enums.OnlineStatus.OFFLINE.getCode()) // 若uuid为空，则只查询在线和在游戏中的用户发布的组队信息
                        .orderByDesc(Team::getUpdateTime) // 排序
        );
        List<TeamVO> teamList = teamPage.getRecords().parallelStream().map(team -> teamThreadTaskService.getTeamVO(team).join()).toList();
        IPage<TeamVO> teamListVOPage = new Page<>(getTeamDTO.getPage(), getTeamDTO.getSize(), teamPage.getTotal());
        teamListVOPage.setRecords(teamList);
        log.info("查询组队信息耗时：{}ms", System.currentTimeMillis() - cur);
        return ResponseVO.wrapData(teamListVOPage);
    }

    @Override
    public ResponseVO<String> toggleTeamStatus(ToggleTeamStatusDTO toggleTeamStatusDTO) {
        Integer teamId = toggleTeamStatusDTO.getTeamId();
        Integer status = toggleTeamStatusDTO.getStatus();

        Team team = this.baseMapper.selectOne(new LambdaQueryWrapper<Team>().eq(Team::getId, teamId));
        if (BeanUtil.isEmpty(team) || !StrUtil.equals(team.getCreatorUuid(), StpUtil.getLoginIdAsString())){
            return ResponseVO.failed(ResponseEnum.TEAM_NOT_FOUND);
        }
        team.setStatus(status);
        this.baseMapper.updateById(team);
        return ResponseVO.completed();
    }


    @Override
    public ResponseVO<String> applicationResult(ApplicationDTO applicationDTO, String to) {
        RLock lock = redissonClient.getLock(TEAM_APPLICATION_PREFIX + applicationDTO.getTeam().getUuid() + ":" + applicationDTO.getBuild().getId());
        lock.lock();
        try {
            ChannelGroup channelGroup;
            Integer server = applicationDTO.getTeam().getServer();
            String route = applicationDTO.getTeam().getChannel();
            switch (NettyServerEnum.GameServerEnum.match(server)) {
                case EN -> channelGroup = ChannelUtil.getEnChannelGroupByRoute(route);
                case CN -> channelGroup = ChannelUtil.getCnChannelGroupByRoute(route);
                default -> throw new TeamException(ResponseEnum.UNKNOWN_GAME_SERVER);
            }

            switch (NettyServerEnum.ApplicationStatus.match(applicationDTO.getStatus())) {
                case pending -> channelGroup.writeAndFlush(WssResponseVO.JOIN(JSON.toJSONString(applicationDTO)));
                case accepted -> {
                    TeamMember member = teamMemberMapper.selectOne(new LambdaQueryWrapper<TeamMember>().eq(TeamMember::getId, applicationDTO.getBuild().getId()));
                    channelGroup.writeAndFlush(WssResponseVO.JOIN_ACCEPT(JSON.toJSONString(applicationDTO)));
                    member.setUserUuid(applicationDTO.getFrom().getUuid());
                    teamMemberMapper.updateById(member);
                }
                case rejected -> channelGroup.writeAndFlush(WssResponseVO.JOIN_REJECT(JSON.toJSONString(applicationDTO)));
                default -> throw new TeamException(ResponseEnum.UNKNOWN_APPLICATION_STATUS);
            }
        } finally {
            lock.unlock();
        }
        // todo: 保存申请信息
        return ResponseVO.completed();
    }


    // todo: 2021/10/3 未完成
    @Override
    public ResponseVO<List<TeamVO>> getJoinTeamRequestListByUserId(Integer userId) {
        return null;
    }
}





