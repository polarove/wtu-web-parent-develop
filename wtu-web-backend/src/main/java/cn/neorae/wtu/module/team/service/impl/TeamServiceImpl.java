package cn.neorae.wtu.module.team.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.neorae.common.enums.Enums;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.TeamWarframe;
import cn.neorae.wtu.module.team.domain.bo.GetTeamListBO;
import cn.neorae.wtu.module.team.domain.bo.TeamBO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.GetTeamDTO;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import cn.neorae.wtu.module.team.mapper.TeamWarframeMapper;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.service.TeamService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private TeamWarframeMapper teamWarframeMapper;

    @Resource
    private TeamRequirementService teamRequirementService;

    @Resource
    private TeamRequirementMapper teamRequirementMapper;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> createTeam(CreateTeamDTO createTeamDTO) {

        Team team = new Team();
        team.setTitle(createTeamDTO.getTitle());
        team.setServer(createTeamDTO.getServer());
        team.setCreatorUuid(StpUtil.getLoginIdAsString());
        team.setChannel(createTeamDTO.getChannel());
        team.setUuid(UUID.randomUUID().toString());
        // 保存队伍
        if (BeanUtil.isNotEmpty(team)){
            this.baseMapper.insert(team);
        }
        List<TeamRequirement> requirementList = createTeamDTO.getRequirements().stream().map(requirement -> {
            TeamRequirement teamRequirement = new TeamRequirement();
            teamRequirement.setTeamId(team.getId());
            BeanUtil.copyProperties(requirement, teamRequirement);
            return teamRequirement;
        }).toList();

        List<TeamMember> memberList = createTeamDTO.getMembers().stream().map(member -> {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(team.getId());
            teamMember.setFocus(member.getFocus());

            Integer warframeId;
            TeamWarframe teamWarframe = teamWarframeMapper.selectOne(new LambdaQueryWrapper<TeamWarframe>().eq(TeamWarframe::getEn, member.getWarframe().getEn()));
            if (!BeanUtil.isNotEmpty(teamWarframe)) {
                teamWarframe = new TeamWarframe();
                teamWarframe.setEn(member.getWarframe().getEn());
                teamWarframe.setCn(member.getWarframe().getCn());
                teamWarframeMapper.insert(teamWarframe);
            }
            warframeId = teamWarframe.getId();
            teamMember.setWarframeId(warframeId);

            teamMember.setRole(member.getRole());
            teamMember.setUserUuid(member.getUser().getUuid());

            return teamMember;
        }).toList();

        if (ArrayUtil.isNotEmpty(requirementList)){
            teamRequirementService.saveBatch(requirementList);
        }
        if (ArrayUtil.isNotEmpty(memberList)){
            teamMemberService.saveBatch(memberList);
        }
        return ResponseVO.completed();
    }

    @Override
    @Transactional
    public ResponseVO<String> removeTeam(Integer teamId) {
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
    public ResponseVO<List<GetTeamListBO>> getTeamList(GetTeamDTO getTeamDTO) {
        List<Team> teams = this.list(new LambdaQueryWrapper<Team>().eq(Team::getChannel, getTeamDTO.getChannel()).eq(Team::getServer, getTeamDTO.getServer()).eq(Team::getStatus, Enums.Polar.TRUE.getCode()));
        List<GetTeamListBO> teamList = teams.stream().map(team -> {
            GetTeamListBO getTeamListBO = new GetTeamListBO();
            getTeamListBO.setTeam(this.getTeamBO(team).join());
            getTeamListBO.setMemberList(teamMemberService.getTeamMemberBOList(team).join());
            getTeamListBO.setRequirementsList(teamRequirementService.getTeamRequirementList(team).join());
            return getTeamListBO;
        }).toList();
        return ResponseVO.wrapData(teamList);
    }

    @Async
    protected CompletableFuture<TeamBO> getTeamBO(Team team) {
        TeamBO teamBO = new TeamBO();
        BeanUtil.copyProperties(team, teamBO);
        return CompletableFuture.completedFuture(teamBO);
    }



}





