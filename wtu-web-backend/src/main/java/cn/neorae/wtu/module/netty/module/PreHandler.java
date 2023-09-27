package cn.neorae.wtu.module.netty.module;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.exceptions.UserNotFoundException;
import com.alibaba.fastjson2.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreHandler {


    public static WebsocketConnectionDTO execute(TextWebSocketFrame msg) throws Exception {
        log.info("msg:{}",msg.text());
        WebsocketConnectionDTO websocketConnectionDTO = JSON.parseObject(msg.text(), WebsocketConnectionDTO.class);
        String uuid = websocketConnectionDTO.getUuid();
        if (StrUtil.isBlank(uuid)){
            throw new UserNotFoundException(ResponseEnum.USER_NOT_FOUND);
        }
        if (!StpUtil.isLogin(uuid)){
            throw new UserNotFoundException(ResponseEnum.USER_NOT_FOUND);
        }
        if (null == websocketConnectionDTO.getServer()){
            throw new UserNotFoundException(ResponseEnum.UNKNOWN_GAME_SERVER);
        }
        return websocketConnectionDTO;
    }
}
