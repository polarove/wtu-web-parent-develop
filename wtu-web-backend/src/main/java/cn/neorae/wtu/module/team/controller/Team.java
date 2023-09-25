package cn.neorae.wtu.module.team.controller;


import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.vo.TeamListVO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.GetTeamDTO;
import cn.neorae.wtu.module.team.service.TeamService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
@Tag(name = "队伍接口")
public class Team {

    @Resource
    private TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseVO<TeamListVO> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        return teamService.createTeam(createTeamDTO);
    }

    @PostMapping("/getTeamList")
    public ResponseVO<Page<TeamListVO>> getTeamList(@RequestBody GetTeamDTO getTeamDTO) {
        return teamService.getTeamList(getTeamDTO);
    }
}
