package cn.neorae.wtu.module.netty.module;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.TestSocketException;
import cn.neorae.wtu.module.netty.exceptions.UserException;
import cn.neorae.wtu.module.netty.exceptions.WssServerException;
import cn.neorae.wtu.module.netty.module.cn.CnTeamConnectionHandler;
import cn.neorae.wtu.module.netty.module.cn.CnTeamDisconnectionHandler;
import cn.neorae.wtu.module.netty.module.en.EnTeamConnectionHandler;
import cn.neorae.wtu.module.netty.module.en.EnTeamDisconnectionHandler;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class PreHandler {


    public static WebsocketConnectionDTO execute(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws UserException, WssServerException, TestSocketException {
        WebsocketConnectionDTO websocketConnectionDTO = JSON.parseObject(msg.text(), WebsocketConnectionDTO.class);
        log.info("route:{}, uuid:{}, server: {}, action: {}", websocketConnectionDTO.getRoute(), websocketConnectionDTO.getUuid(), websocketConnectionDTO.getServer(), websocketConnectionDTO.getAction());
        if (websocketConnectionDTO.getAction().equals(NettyServerEnum.ConnectionEnum.PING.getType())){
            ctx.writeAndFlush(WssResponseVO.CONNECT_SUCCEED(ResponseEnum.PONG));
        }
        String uuid = websocketConnectionDTO.getUuid();
        Integer server = websocketConnectionDTO.getServer();
        Integer action = websocketConnectionDTO.getAction();
        if (StrUtil.isBlank(uuid)){
            throw new UserException(ResponseEnum.USER_NOT_FOUND);
        }
        if (!StpUtil.isLogin(uuid)){
            throw new UserException(ResponseEnum.USER_NOT_LOGIN);
        }
        if (null == server){
            throw new WssServerException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        switch (NettyServerEnum.GameServerEnum.match(server)){
            case EN -> {
                if(action.equals(NettyServerEnum.ConnectionEnum.CONNECT.getType())){
                    EnTeamConnectionHandler.execute(ctx, msg);
                }else if (action.equals(NettyServerEnum.ConnectionEnum.DISCONNECT.getType())){
                    EnTeamDisconnectionHandler.execute(ctx, msg);
                }else{
                    throw new WssServerException(ResponseEnum.NOT_SUPPORTED);
                }
            }
            case CN -> {
                if(action.equals(NettyServerEnum.ConnectionEnum.CONNECT.getType())){
                    CnTeamConnectionHandler.execute(ctx, msg);
                }else if (action.equals(NettyServerEnum.ConnectionEnum.DISCONNECT.getType())){
                    CnTeamDisconnectionHandler.execute(ctx, msg);
                }else{
                    throw new WssServerException(ResponseEnum.NOT_SUPPORTED);
                }
            }
        }
        return websocketConnectionDTO;
    }
}
