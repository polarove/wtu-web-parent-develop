package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class UserNotLoginException extends RuntimeException {


    private final ResponseEnum responseEnum;


    public UserNotLoginException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws UserNotLoginException {
        throw new UserNotLoginException(ResponseEnum.USER_NOT_LOGIN);
    }


}
