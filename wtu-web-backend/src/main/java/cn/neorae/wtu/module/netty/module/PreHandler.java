package cn.neorae.wtu.module.netty.module;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.TestSocketException;
import cn.neorae.wtu.module.netty.exceptions.UserNotFoundException;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class PreHandler {


    public static WebsocketConnectionDTO execute(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("msg:{}",msg.text());

        WebsocketConnectionDTO websocketConnectionDTO = JSON.parseObject(msg.text(), WebsocketConnectionDTO.class);
        if (websocketConnectionDTO.getAction().equals(NettyServerEnum.ConnectionEnum.PING.getType())){
            throw new TestSocketException(ResponseEnum.PONG);
        }
        String uuid = websocketConnectionDTO.getUuid();
        if (StrUtil.isBlank(uuid)){
            throw new UserNotFoundException(ResponseEnum.USER_NOT_FOUND);
        }
        if (!StpUtil.isLogin(uuid)){
            throw new NotLoginException("用户未登录", null, null);
        }
        if (null == websocketConnectionDTO.getServer()){
            throw new UserNotFoundException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        return websocketConnectionDTO;
    }
}
