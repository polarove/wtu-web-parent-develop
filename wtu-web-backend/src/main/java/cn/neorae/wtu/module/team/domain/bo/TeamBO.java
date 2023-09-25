package cn.neorae.wtu.module.team.domain.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class TeamBO {

    private Integer id;

    private String uuid;


    private Integer server;


    private String channel;


    private String title;


    private Integer status;


    private String creatorUuid;


    private Integer isDeleted;

}
