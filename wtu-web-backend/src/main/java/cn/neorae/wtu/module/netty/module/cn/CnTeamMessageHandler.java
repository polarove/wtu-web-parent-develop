package cn.neorae.wtu.module.netty.module.cn;

import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

@Component
public class CnTeamMessageHandler {

    public static void execute(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg)  {
        WebsocketRequestDTO dto = JSON.parseObject(msg.text(), WebsocketRequestDTO.class);
    }
}
