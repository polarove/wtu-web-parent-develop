package cn.neorae.wtu.module.netty.module.en;

import cn.neorae.wtu.module.account.mapper.UserMapper;
import cn.neorae.wtu.module.account.service.UserService;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketRequestDTO;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnTeamMessageHandler {

    public static void execute(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {

    }

}
