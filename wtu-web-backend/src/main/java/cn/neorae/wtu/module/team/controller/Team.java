package cn.neorae.wtu.module.team.controller;


import cn.neorae.common.annotation.FreePass;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.dto.ToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.GetTeamDTO;
import cn.neorae.wtu.module.team.service.TeamService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@Tag(name = "队伍接口")
public class Team {

    @Resource
    private TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseVO<Integer> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        return teamService.createTeam(createTeamDTO);
    }

    @GetMapping("/getTeamById")
    public ResponseVO<TeamVO> getTeamById(@RequestParam Integer teamId) {
        return teamService.getTeamById(teamId);
    }

    @PostMapping("/getTeamList")
    @FreePass
    public ResponseVO<Page<TeamVO>> getTeamList(@RequestBody GetTeamDTO getTeamDTO) {
        return teamService.getTeamList(getTeamDTO);
    }

    @PostMapping("/toggleTeamStatus")
    public ResponseVO<String> toggleTeamStatus(@RequestBody ToggleTeamStatusDTO toggleTeamStatusDTO) {
        return teamService.toggleTeamStatus(toggleTeamStatusDTO);
    }

    @GetMapping("/removeTeam")
    public ResponseVO<String> removeTeam(@RequestParam Integer teamId) {
        return teamService.removeTeamById(teamId);
    }


}
