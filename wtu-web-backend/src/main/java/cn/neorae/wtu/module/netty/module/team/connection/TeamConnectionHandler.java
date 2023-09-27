package cn.neorae.wtu.module.netty.module.team.connection;


import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.exceptions.NotLoginException;
import io.netty.channel.ChannelHandlerContext;

public class TeamConnectionHandler {
    

    

    public static void execute(ChannelHandlerContext channelHandlerContext, WebsocketRequestDTO websocketRequestDTO) throws NotLoginException {
//        channelHandlerContext.channel().writeAndFlush(WssResponseVO.hello());
//        NotLoginException.throwException();
//        throw new NotLoginException(ResponseEnum.BAD_PARAM);

    }

}
