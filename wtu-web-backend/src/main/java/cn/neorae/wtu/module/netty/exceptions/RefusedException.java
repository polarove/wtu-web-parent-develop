package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class RefusedException extends RuntimeException {

    private final ResponseEnum responseEnum;

    public RefusedException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws RefusedException {
        throw new RefusedException(ResponseEnum.USER_REJECTED);
    }
}
