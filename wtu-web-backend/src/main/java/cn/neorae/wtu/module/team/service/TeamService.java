package cn.neorae.wtu.module.team.service;

import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.vo.TeamListVO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.GetTeamDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service
* @createDate 2023-09-25 08:50:11
*/
public interface TeamService extends IService<Team> {

    ResponseVO<TeamListVO> createTeam(CreateTeamDTO createTeamDTO);

    ResponseVO<String> removeTeamById(Integer teamId);

    ResponseVO<List<TeamListVO>> getTeamList(GetTeamDTO getTeamDTO);
}
