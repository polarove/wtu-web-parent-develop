package cn.neorae.wtu.module.netty.team.handler.exceptions;

import cn.neorae.wtu.module.netty.team.domain.vo.WssResponseVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.neorae.common.enums.ResponseEnum;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class NettyGlobalExceptionHandler {
    

    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public TextWebSocketFrame notLoginExceptionHandler(ResponseEnum responseEnum) {
        return WssResponseVO.fail(responseEnum);
    }
}
