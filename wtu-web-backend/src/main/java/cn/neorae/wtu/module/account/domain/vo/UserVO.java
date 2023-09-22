package cn.neorae.wtu.module.account.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private String uuid;

    private String name;

    private String avatar;

    private String description;

    private Integer onlineStatus;

    private Integer server;

    private List<String> boosterList;
}
