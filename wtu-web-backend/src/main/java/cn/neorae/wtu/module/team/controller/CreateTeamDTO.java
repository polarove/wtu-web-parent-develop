package cn.neorae.wtu.module.team.controller;

import lombok.Data;

@Data
public class CreateTeamDTO {


    public String title;

    public CreateTeamBO me;

    public CreateTeamBO firstMate;

    public CreateTeamBO secondMate;

    public CreateTeamBO thirdMate;

}
