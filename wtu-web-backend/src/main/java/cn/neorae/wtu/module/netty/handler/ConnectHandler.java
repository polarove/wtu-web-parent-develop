package cn.neorae.wtu.module.netty.handler;

import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.netty.NettyServer;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import io.netty.channel.ChannelHandlerContext;

public class ConnectHandler {
    

    public static void execute(ChannelHandlerContext channelHandlerContext, WebsocketRequestDTO websocketRequestDTO) {
        
        

        NettyServer.TEAM_ORIGIN.add(channelHandlerContext.channel());
        channelHandlerContext.channel().writeAndFlush(ResponseVO.wrapData("已连接至Oringin服务器"));

    }
}
