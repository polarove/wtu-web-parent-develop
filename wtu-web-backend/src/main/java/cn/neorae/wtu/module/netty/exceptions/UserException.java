package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {


    private final ResponseEnum responseEnum;


    public UserException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws UserException {
        throw new UserException(ResponseEnum.USER_NOT_LOGIN);
    }


}
