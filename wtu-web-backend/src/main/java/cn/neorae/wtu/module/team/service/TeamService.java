package cn.neorae.wtu.module.team.service;

import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastDeleteTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.dto.create.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.get.GetTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.join.JoinTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.toggle.ToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service
* @createDate 2023-09-25 08:50:11
*/
public interface TeamService extends IService<Team> {

    ResponseVO<Integer> createTeam(CreateTeamDTO createTeamDTO);

    ResponseVO<String> removeTeamById(Integer teamId);

    ResponseVO<TeamVO> getTeamById(Integer teamId);

    ResponseVO<IPage<TeamVO>> getTeamList(GetTeamDTO getTeamDTO);

    ResponseVO<String> toggleTeamStatus(ToggleTeamStatusDTO toggleTeamStatusDTO);

    ResponseVO<String> joinTeam(JoinTeamDTO joinTeamDTO);

    ResponseVO<String> broadcastTeam(BroadcastTeamDTO broadcastTeamDTO);

    ResponseVO<String> broadcastDeleteTeam(BroadcastDeleteTeamDTO broadcastDeleteTeamDTO);

    ResponseVO<String> broadcastToggleTeamStatus(BroadcastToggleTeamStatusDTO broadcastToggleTeamStatusDTO);
}
