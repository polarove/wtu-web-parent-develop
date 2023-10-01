package cn.neorae.wtu.module.netty.domain.vo.connection;


import cn.neorae.wtu.module.netty.NettyApplication;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import lombok.Data;

@Data
public class AfterConnectionBO {

    private Integer total;

    private Integer clients;
}
