package cn.neorae.wtu.module.team.service;

import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service
* @createDate 2023-09-25 08:50:11
*/
public interface TeamService extends IService<Team> {

    ResponseVO<String> createTeam(CreateTeamDTO createTeamDTO);

    ResponseVO<String> removeTeam(String teamUuid);
}
