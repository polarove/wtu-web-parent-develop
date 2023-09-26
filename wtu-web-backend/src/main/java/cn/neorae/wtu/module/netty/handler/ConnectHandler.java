package cn.neorae.wtu.module.netty.handler;

import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.netty.NettyServer;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectHandler {
    

    public static void execute(ChannelHandlerContext channelHandlerContext, WebsocketRequestDTO websocketRequestDTO) {
        
        NettyServer.USER_CHANNEL_MAP.put(websocketRequestDTO.getUuid(), channelHandlerContext.channel());
        // NettyServer.TEAM_ORIGIN.add(channelHandlerContext.channel());
        // NettyServer.TEAM_EVENT.add(channelHandlerContext.channel());
        channelHandlerContext.channel().writeAndFlush(WssResponseVO.fail("已连接至Oringin服务器"));
    }
}
