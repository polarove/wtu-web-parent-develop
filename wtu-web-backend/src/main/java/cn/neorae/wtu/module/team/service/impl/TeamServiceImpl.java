package cn.neorae.wtu.module.team.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.Enums;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.mapper.UserMapper;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.bo.TeamBO;
import cn.neorae.wtu.module.team.domain.bo.TeamMemberBO;
import cn.neorae.wtu.module.team.domain.bo.TeamRequirementsBO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.GetTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.ToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.service.TeamService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2023-09-25 08:50:11
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{


    @Resource
    private TeamRequirementService teamRequirementService;

    @Resource
    private TeamRequirementMapper teamRequirementMapper;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeamMapper teamMapper;

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

        // 保存队伍
        if (BeanUtil.isNotEmpty(team)){
            this.baseMapper.insert(team);
        }

        // 装配队伍需求
        List<TeamRequirement> requirementList = createTeamDTO.getRequirements().stream().map(requirement -> {
            TeamRequirement teamRequirement = new TeamRequirement();
            teamRequirement.setTeamId(team.getId());
            BeanUtil.copyProperties(requirement, teamRequirement);
            return teamRequirement;
        }).toList();

        // 装配队伍成员信息
        List<TeamMember> memberList = createTeamDTO.getMembers().stream().map(member -> {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(team.getId());
            teamMember.setUserUuid(member.getUser().getUuid());
            teamMember.setEn(member.getWarframe().getEn());
            teamMember.setCn(member.getWarframe().getCn());
            BeanUtil.copyProperties(member, teamMember);
            return teamMember;
        }).toList();

        // 保存队伍需求和队伍成员信息
        if (ArrayUtil.isNotEmpty(requirementList)){
            teamRequirementService.saveBatch(requirementList);
        }
        if (ArrayUtil.isNotEmpty(memberList)){
            teamMemberService.saveBatch(memberList);
        }else{
            return ResponseVO.failed(ResponseEnum.TEAM_MEMBER_NOT_SATISFIED);
        }

        // todo: 广播到当前channel的所有用户
        return ResponseVO.wrapData(team.getId());
    }

    @Override
    @Transactional
    public ResponseVO<String> removeTeamById(Integer teamId) {
        Team team = this.getOne(new LambdaQueryWrapper<Team>().eq(Team::getId, teamId));

        List<Integer> requirementIdList = teamRequirementMapper
                .selectList(new LambdaQueryWrapper<TeamRequirement>().eq(TeamRequirement::getTeamId, teamId))
                .stream()
                .peek(teamRequirement -> {
                }).map(TeamRequirement::getId).toList();

        List<Integer> memberIdList = teamMemberMapper
                .selectList(new LambdaQueryWrapper<TeamMember>().eq(TeamMember::getTeamId, teamId))
                .stream()
                .peek(teamRequirement -> {
                }).map(TeamMember::getId).toList();


        if (BeanUtil.isNotEmpty(team)){
            removeById(team);
        }
        if (ArrayUtil.isNotEmpty(requirementIdList)){
            teamRequirementService.removeBatchByIds(requirementIdList);
        }
        if (ArrayUtil.isNotEmpty(memberIdList)){
            teamMemberService.removeBatchByIds(memberIdList);
        }
        return ResponseVO.completed();
    }

    @Override
    public ResponseVO<TeamVO> getTeamById(Integer teamId) {
        Team team = this.baseMapper.selectOne(new LambdaQueryWrapper<Team>().eq(Team::getId, teamId));
        if (BeanUtil.isEmpty(team)){
            return ResponseVO.failed(ResponseEnum.TEAM_NOT_FOUND);
        }
        TeamVO teamVO = new TeamVO();
        teamVO.setTeam(this.getTeamBO(team).join());
        List<TeamMemberBO> teamMemberBOS = teamMemberService.getTeamMemberBOList(team).join();
        List<TeamRequirementsBO> teamRequirementsBOS = teamRequirementService.getTeamRequirementList(team).join();
        teamVO.setMembers(teamMemberBOS);
        teamVO.setRequirements(teamRequirementsBOS);
        return ResponseVO.wrapData(teamVO);
    }

    @Override
    public ResponseVO<IPage<TeamVO>> getTeamList(GetTeamDTO getTeamDTO) {
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

        List<TeamVO> teamList = teamPage.getRecords().stream().map(team -> {
            TeamVO teamVO = new TeamVO();
            teamVO.setTeam(this.getTeamBO(team).join());
            teamVO.setMembers(teamMemberService.getTeamMemberBOList(team).join());
            teamVO.setRequirements(teamRequirementService.getTeamRequirementList(team).join());
            return teamVO;
        }).toList();


        IPage<TeamVO> teamListVOPage = new Page<>(getTeamDTO.getPage(), getTeamDTO.getSize(), teamPage.getTotal());
        teamListVOPage.setRecords(teamList);


        return ResponseVO.wrapData(teamListVOPage);
    }

    @Override
    public ResponseVO<String> toggleTeamStatus(ToggleTeamStatusDTO toggleTeamStatusDTO) {
        String uuid = toggleTeamStatusDTO.getUuid();
        Integer status = toggleTeamStatusDTO.getStatus();

        Team team = this.baseMapper.selectOne(new LambdaQueryWrapper<Team>().eq(Team::getUuid, uuid));
        if (BeanUtil.isEmpty(team)){
            return ResponseVO.failed(ResponseEnum.TEAM_NOT_FOUND);
        }
        team.setStatus(status);
        this.baseMapper.updateById(team);
        return ResponseVO.completed();
    }

    @Async
    protected CompletableFuture<TeamBO> getTeamBO(Team team) {
        TeamBO teamBO = new TeamBO();
        BeanUtil.copyProperties(team, teamBO);
        return CompletableFuture.completedFuture(teamBO);
    }



}





