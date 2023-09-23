package cn.neorae.wtu.module.team.controller;


import cn.neorae.common.response.ResponseVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
@Tag(name = "队伍接口")
public class team {


    @PostMapping("/createTeam")
    public ResponseVO<CreateTeamDTO> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        return ResponseVO.wrapData(createTeamDTO);
    }
}
