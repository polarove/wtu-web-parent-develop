package cn.neorae.wtu.module.netty.team.handler;


import cn.neorae.wtu.module.netty.team.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.team.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.team.handler.exceptions.NotLoginException;
import io.netty.channel.ChannelHandlerContext;

public class ConnectionHandler {
    

    

    public static void execute(ChannelHandlerContext channelHandlerContext, WebsocketRequestDTO websocketRequestDTO) throws NotLoginException {
        channelHandlerContext.channel().writeAndFlush(WssResponseVO.hello());
    }
}
