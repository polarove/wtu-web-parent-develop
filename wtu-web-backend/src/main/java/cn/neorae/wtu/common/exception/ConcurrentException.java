package cn.neorae.wtu.common.exception;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class ConcurrentException extends RuntimeException{

    private final ResponseEnum responseEnum;

    public ConcurrentException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public static void throwException() {
        throw new TeamException(ResponseEnum.RESOURCE_LOCKED);
    }
}
