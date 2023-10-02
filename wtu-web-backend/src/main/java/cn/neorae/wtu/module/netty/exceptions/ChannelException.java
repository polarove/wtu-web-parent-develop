package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class ChannelException extends RuntimeException{



    private final ResponseEnum responseEnum;



    public ChannelException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws ChannelException {
        throw new ChannelException(ResponseEnum.CHANNEL_NOT_FOUND);
    }
}
