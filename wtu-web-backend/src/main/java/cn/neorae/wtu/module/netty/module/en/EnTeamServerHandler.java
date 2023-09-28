package cn.neorae.wtu.module.netty.module.en;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.account.mapper.UserMapper;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.UserNotFoundException;
import cn.neorae.wtu.module.netty.exceptions.UserNotLoginException;
import cn.neorae.wtu.module.netty.module.PreHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Sharable
@Component
public class EnTeamServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 行为控制器
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws UserNotFoundException, UserNotLoginException {
        try{
            WebsocketConnectionDTO websocketConnectionDTO = PreHandler.execute(ctx, msg);
            if (websocketConnectionDTO.getServer().equals(NettyServerEnum.GameServerEnum.CN.getType())){
                return;
            }
            EnChannelMap.init();
            switch (NettyServerEnum.ConnectionEnum.match(websocketConnectionDTO.getAction())) {
                case CONNECT -> EnTeamConnectionHandler.execute(ctx, msg);
                case DISCONNECT -> EnTeamDisconnectionHandler.execute(ctx, msg);
                case MESSAGE -> EnTeamMessageHandler.execute(ctx, msg);
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
