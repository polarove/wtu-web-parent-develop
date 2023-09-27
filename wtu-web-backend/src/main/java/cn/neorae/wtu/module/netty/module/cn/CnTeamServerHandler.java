package cn.neorae.wtu.module.netty.module.cn;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.UserNotFoundException;
import cn.neorae.wtu.module.netty.exceptions.UserNotLoginException;
import cn.neorae.wtu.module.netty.module.PreHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CnTeamServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws UserNotFoundException, UserNotLoginException {
        try{
            WebsocketConnectionDTO websocketConnectionDTO = PreHandler.execute(msg);
            if (websocketConnectionDTO.getServer().equals(NettyServerEnum.GameServerEnum.EN.getType())){
                return;
            }
            log.info("server type: {}",websocketConnectionDTO.getServer());
            switch (NettyServerEnum.ConnectionEnum.match(websocketConnectionDTO.getAction())) {
                case CONNECT -> CnTeamConnectionHandler.execute(ctx, msg);
                case DISCONNECT -> CnTeamDisconnectionHandler.execute(ctx, msg);
                case MESSAGE -> CnTeamMessageHandler.execute(ctx, msg);
                default -> ctx.channel().writeAndFlush(WssResponseVO.fail(ResponseEnum.NOT_SUPPORTED));
            }
        } catch (UserNotLoginException e) {
            ctx.channel().writeAndFlush(WssResponseVO.fail(e.getResponseEnum()));
        } catch (UserNotFoundException e) {
            ctx.channel().writeAndFlush(WssResponseVO.fail(e.getResponseEnum()));
        } catch (Exception e) {
            log.info("error:{}",e.getMessage());
        }
    }


}
