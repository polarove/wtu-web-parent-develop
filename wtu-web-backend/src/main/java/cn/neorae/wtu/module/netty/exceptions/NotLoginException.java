package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class NotLoginException extends RuntimeException {
    

    private final ResponseEnum responseEnum;


    public NotLoginException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws NotLoginException {
        throw new NotLoginException(ResponseEnum.NOT_LOGIN);
    }


}
