package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;

public class OffLineException extends RuntimeException {

    private final ResponseEnum responseEnum;

    public OffLineException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws NotLoginException {
        throw new NotLoginException(ResponseEnum.USER_OFFLINE);
    }


}