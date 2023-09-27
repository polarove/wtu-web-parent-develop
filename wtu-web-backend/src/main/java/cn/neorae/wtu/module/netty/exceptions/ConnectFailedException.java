package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class ConnectFailedException extends RuntimeException {



    private final ResponseEnum responseEnum;



    public ConnectFailedException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws ChannelNotFoundException {
        throw new ChannelNotFoundException(ResponseEnum.CHANNEL_NOT_FOUND);
    }
}
