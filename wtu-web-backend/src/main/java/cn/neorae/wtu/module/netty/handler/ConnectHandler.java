package cn.neorae.wtu.module.netty.handler;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.NettyServer;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.handler.exceptions.NotLoginException;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectHandler {
    

    

    public static void execute(ChannelHandlerContext channelHandlerContext, WebsocketRequestDTO websocketRequestDTO) throws NotLoginException {
        
        NettyServer.USER_CHANNEL_MAP.put(websocketRequestDTO.getUuid(), channelHandlerContext.channel());
        // NettyServer.TEAM_ORIGIN.add(channelHandlerContext.channel());
        // NettyServer.TEAM_EVENT.add(channelHandlerContext.channel());
        NotLoginException exception = new NotLoginException(ResponseEnum.NOT_LOGIN);
        exception.throwException();
        channelHandlerContext.channel().writeAndFlush(WssResponseVO.fail(ResponseEnum.ORIGIN_CONNECTED));
    }
}
