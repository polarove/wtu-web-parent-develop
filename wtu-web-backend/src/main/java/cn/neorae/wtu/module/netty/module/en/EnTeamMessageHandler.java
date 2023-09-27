package cn.neorae.wtu.module.netty.module.en;

import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnTeamMessageHandler {


    public static void execute(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg)  {
        WebsocketRequestDTO dto = JSON.parseObject(msg.text(), WebsocketRequestDTO.class);
    }
}
