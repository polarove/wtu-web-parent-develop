package cn.neorae.wtu.module.netty.handler.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson2.JSONObject;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;

@ControllerAdvice
public class NettyGlobalExceptionHandler {
    

    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public JSONObject notLoginExceptionHandler(ResponseEnum responseEnum) {
        return WssResponseVO.fail(responseEnum);
    }
}
