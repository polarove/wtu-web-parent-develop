package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;

public class RefusedException extends RuntimeException {

    private final ResponseEnum responseEnum;

    public RefusedException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws NotLoginException {
        throw new NotLoginException(ResponseEnum.USER_REJECTED);
    }
}
