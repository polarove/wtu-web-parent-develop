package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class WssServerException extends RuntimeException {

    private final ResponseEnum responseEnum;

    public WssServerException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws WssServerException {
        throw new WssServerException(ResponseEnum.USER_OFFLINE);
    }


}