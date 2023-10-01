package cn.neorae.wtu.common.exception;

import cn.neorae.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class TeamException extends RuntimeException{


    private final ResponseEnum responseEnum;

    public TeamException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public static void throwException() throws TeamException {
        throw new TeamException(ResponseEnum.TEAM_NOT_FOUND);
    }
}
