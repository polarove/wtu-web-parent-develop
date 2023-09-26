package cn.neorae.wtu.module.netty.handler;

import org.springframework.boot.autoconfigure.web.ServerProperties.Netty;

import com.alibaba.fastjson.JSON;

import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.netty.NettyServer;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import cn.neorae.wtu.module.netty.enums.WebsocketRequestType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        try{
            WebsocketRequestDTO websocketRequestDTO = JSON.parseObject(msg.text(), WebsocketRequestDTO.class);
            switch (WebsocketRequestType.match(websocketRequestDTO.getType())) {
                case INSERT -> {
                    log.info("request: insert");
                    break;
                }
                case UPDATE -> {
                    log.info("request: update");
                    break;
                }
                case DELETE -> {
                    log.info("request: delete");
                    break;
                }
                case SELECT -> {
                    log.info("request: select");
                    break;
                }
                case CONNECT -> {
                    ConnectHandler.execute(ctx, websocketRequestDTO);
                    break;
                }
                case DISCONNECT -> {
                    log.info("request: disconnected");
                    break;
                }
                case NOT_SUPPORTED -> {
                    log.info("request: not supported");
                    break;
                }
                default -> {
                    log.info("request: default");
                    break;
                }
            }
        } catch (Exception e) {
            ctx.channel().writeAndFlush(msg);
            e.printStackTrace();
        }
    }
    
}
