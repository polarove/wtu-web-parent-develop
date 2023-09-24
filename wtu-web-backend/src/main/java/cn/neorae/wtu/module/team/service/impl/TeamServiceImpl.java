package cn.neorae.wtu.module.team.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamMemberDTO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamRequirementDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.service.TeamService;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2023-09-25 00:38:14
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{

    @Override
    public ResponseVO<CreateTeamDTO> createTeam(CreateTeamDTO createTeamDTO) {

        Team team = new Team();
        String uuid = UUID.randomUUID().toString();
        String channel = createTeamDTO.getChannel();
        String title = createTeamDTO.getTitle();
        Integer server = createTeamDTO.getServer();
        team.setUuid(uuid);
        team.setChannel(channel);
        team.setTitle(title);
        team.setServer(server);
        team.setCreatorUuid(StpUtil.getLoginIdAsString());

        List<CreateTeamRequirementDTO> requirements = createTeamDTO.getRequirements();

        List<CreateTeamMemberDTO> members = createTeamDTO.getMembers();
        return null;
    }
}




