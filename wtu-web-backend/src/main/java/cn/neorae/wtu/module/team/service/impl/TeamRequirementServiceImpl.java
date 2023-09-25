package cn.neorae.wtu.module.team.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import org.springframework.stereotype.Service;

/**
* @author Neorae
* @description 针对表【team_requirement(新建队伍时的要求表)】的数据库操作Service实现
* @createDate 2023-09-25 08:50:18
*/
@Service
public class TeamRequirementServiceImpl extends ServiceImpl<TeamRequirementMapper, TeamRequirement>
    implements TeamRequirementService{

}




