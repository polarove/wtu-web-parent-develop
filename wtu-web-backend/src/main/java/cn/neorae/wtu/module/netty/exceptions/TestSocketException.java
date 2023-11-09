package cn.neorae.wtu.module.netty.exceptions;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class TestSocketException extends RuntimeException{

    private final ResponseEnum responseEnum;


    public TestSocketException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws TestSocketException {
        throw new TestSocketException(ResponseEnum.USER_NOT_FOUND);
    }

}
