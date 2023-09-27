package cn.neorae.wtu.module.netty.module.team.connection;

import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.TeamServerEnum;
import cn.neorae.wtu.module.netty.exceptions.NotLoginException;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TeamServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws NotLoginException {
        try{
            WebsocketRequestDTO websocketRequestDTO = JSON.parseObject(msg.text(), WebsocketRequestDTO.class);
            if (StrUtil.isBlank(websocketRequestDTO.getUuid())) {
                return;
            }
            switch (TeamServerEnum.ConnectionEnum.match(websocketRequestDTO.getAction())) {
                case CONNECT, DISCONNECT -> TeamConnectionHandler.execute(ctx, websocketRequestDTO);
                default -> ctx.channel().writeAndFlush(WssResponseVO.fail(ResponseEnum.BAD_NETTY_PARAM));
            }
        } catch (NotLoginException e) {
            ctx.channel().writeAndFlush(WssResponseVO.fail(e.getResponseEnum()));
        }
    }
    
}
