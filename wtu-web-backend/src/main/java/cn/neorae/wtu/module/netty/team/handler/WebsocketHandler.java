package cn.neorae.wtu.module.netty.team.handler;

import cn.neorae.wtu.module.netty.team.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.team.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.team.enums.WebsocketRequestType;
import cn.neorae.wtu.module.netty.team.handler.exceptions.NotLoginException;
import com.alibaba.fastjson.JSON;

import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.ResponseEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws NotLoginException {
        try{
            WebsocketRequestDTO websocketRequestDTO = JSON.parseObject(msg.text(), WebsocketRequestDTO.class);
            if (StrUtil.isBlank(websocketRequestDTO.getUuid())) {
                return;
            }
            switch (WebsocketRequestType.match(websocketRequestDTO.getAction())) {
                case CONNECT, DISCONNECT -> {
                    ConnectionHandler.execute(ctx, websocketRequestDTO);
                }
                case NOT_SUPPORTED -> {
                    ctx.channel().writeAndFlush(WssResponseVO.fail(ResponseEnum.NOT_SUPPORTED));
                }
                default -> {
                    ctx.channel().writeAndFlush(WssResponseVO.fail(ResponseEnum.CHANNEL_NOT_FOUND));
                }
            }
        } catch (NotLoginException e) {
            ctx.channel().writeAndFlush(WssResponseVO.fail(e.getResponseEnum()));
        }
    }
    
}
