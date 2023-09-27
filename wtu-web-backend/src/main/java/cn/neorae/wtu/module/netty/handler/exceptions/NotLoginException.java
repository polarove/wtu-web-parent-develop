package cn.neorae.wtu.module.netty.handler.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class NotLoginException extends RuntimeException {
    

    private ResponseEnum responseEnum;


    public NotLoginException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public void throwException() throws NotLoginException {
        throw new NotLoginException(this.responseEnum);
    }


}
