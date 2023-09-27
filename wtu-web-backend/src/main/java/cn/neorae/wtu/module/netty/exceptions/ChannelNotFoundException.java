package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class ChannelNotFoundException extends RuntimeException{



    private final ResponseEnum responseEnum;



    public ChannelNotFoundException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws ChannelNotFoundException {
        throw new ChannelNotFoundException(ResponseEnum.CHANNEL_NOT_FOUND);
    }
}
