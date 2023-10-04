package cn.neorae.wtu.module.netty.module.en;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.UserException;
import cn.neorae.wtu.module.netty.module.PreHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Sharable
@Component
public class EnTeamServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 行为控制器
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        WebsocketConnectionDTO websocketConnectionDTO;
        try {
            websocketConnectionDTO = PreHandler.execute(ctx, msg);
            if (websocketConnectionDTO.getServer().equals(NettyServerEnum.GameServerEnum.CN.getType())){
                log.info("cn server, return");
                return;
            }
        } catch (UserException e) {
            ctx.channel().writeAndFlush(WssResponseVO.CONNECT_FAIL(e.getResponseEnum()));
            return;
        }
        try {
            switch (NettyServerEnum.ConnectionEnum.match(websocketConnectionDTO.getAction())) {
                case CONNECT -> EnTeamConnectionHandler.execute(ctx, msg);
                case DISCONNECT -> EnTeamDisconnectionHandler.execute(ctx, msg);
                default -> ctx.channel().writeAndFlush(WssResponseVO.CONNECT_FAIL(ResponseEnum.NOT_SUPPORTED));
            }
        } catch (Exception e) {
            log.info("error:{}",e.getMessage());
        }
    }
}
