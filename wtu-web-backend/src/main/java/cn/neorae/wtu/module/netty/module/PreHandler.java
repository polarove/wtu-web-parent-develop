package cn.neorae.wtu.module.netty.module;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.common.util.UserUtil;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.TestSocketException;
import cn.neorae.wtu.module.netty.exceptions.UserException;
import cn.neorae.wtu.module.netty.exceptions.WssServerException;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class PreHandler {


    public static WebsocketConnectionDTO execute(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws UserException, WssServerException, TestSocketException {
        log.info("msg:{}",msg.text());

        WebsocketConnectionDTO websocketConnectionDTO = JSON.parseObject(msg.text(), WebsocketConnectionDTO.class);
        if (websocketConnectionDTO.getAction().equals(NettyServerEnum.ConnectionEnum.PING.getType())){
            ctx.writeAndFlush(WssResponseVO.CONNECT_SUCCEED(ResponseEnum.PONG));
        }
        String uuid = websocketConnectionDTO.getUuid();
        if (StrUtil.isBlank(uuid)){
            throw new UserException(ResponseEnum.USER_NOT_FOUND);
        }
        if (!StpUtil.isLogin(uuid)){
            throw new UserException(ResponseEnum.USER_NOT_LOGIN);
        }
        if (null == websocketConnectionDTO.getServer()){
            throw new WssServerException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        return websocketConnectionDTO;
    }
}
