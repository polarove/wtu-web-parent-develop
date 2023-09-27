package cn.neorae.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ResponseEnum {


    /**
     * 返回码和状态说明
     */
    SUCCESS(200, "成功"),
    UPDATE_SUCCESS(201,"修改成功"),
    CREATE_SUCCESS(202,"创建成功"),
    DELETE_SUCCESS(203,"删除成功"),
    VERIFICATION_PASSED(204,"验证完成，Tenno！"),
    VERIFICATION_DUPLICATED(205,"当前帐号已通过验证"),
    VERIFICATION_FAILED(206,"验证时发生错误，加群问问狗群主吧"),
    USER_NOT_FOUND(207,"用户不存在"),
    USER_NOT_LOGIN(208,"尚未登录"),
    BAD_PARAM(301, "请求参数错误"),
    INCORPORATE_CODE(302, "验证码错误"),
    USER_EXISTS(303, "邮箱已被注册"),
    LOGIN_ERROR(304, "登陆失败，用户名或密码无效"),
    BAD_REQUEST(305, "请求方法错误"),
    SERVICE_ERROR(306, "服务错误"),
    SERVICE_UNAVAILABLE(307, "服务不可用"),
    SERVICE_RESTARTED(308, "请刷新页面"),
    UNAUTHORIZED(401, "尚未认证，请重新登录"),
    FORBIDDEN(402, "权限不足，禁止访问"),
    TIME_OUT(403, "请求超时"),
    NOT_FOUND(404, "请求的资源不存在"),
    VERIFICATION_REQUIRED(405, "账户需要验证"),
    FAILED(500, "请求失败"),

    INVALID_BOOSTER(1001, "无效的加成类型"),
    TEAM_NOT_FOUND(1002, "队伍不存在"),
    TEAM_MEMBER_NOT_SATISFIED(1003, "成员数量不足"),


    // Netty Server Error Code
    NOT_LOGIN(2001, "尚未登录"),
    NOT_SUPPORTED(2002, "不支持的请求类型"),
    CHANNEL_NOT_FOUND(2003, "未找到对应的Channel"),
    CHANNEL_NOT_CONNECTED(2004, "未连接至服务器"),
    CHANNEL_NOT_ACTIVE(2005, "Channel未激活"),
    CHANNEL_NOT_WRITABLE(2006, "Channel不可写"),
    CHANNEL_NOT_READABLE(2007, "Channel不可读"),
    CHANNEL_NOT_OPEN(2008, "Channel未打开"),
    CHANNEL_NOT_BOUND(2009, "Channel未绑定"),
    BAD_NETTY_TYPE(2010, "请求类型错误"),
    BAD_NETTY_PARAM(2011, "请求参数错误"),
    BAD_NETTY_BODY(2012, "请求体错误"),

    // Netty Server Success Code
    ORIGIN_CONNECTED(3001, "已连接至始源星系服务器");





    private final Integer code;

    @Getter
    private final String message;

    ResponseEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getCode()
    {
        return code;
    }

    @Getter
    private static final Map<Integer, String> codeMap;

    static {
        codeMap = Arrays.stream(ResponseEnum.values()).collect(Collectors.toMap(ResponseEnum::getCode, ResponseEnum::getMessage));
    }

    public String getBaseMessage(Integer code)
    {
        return codeMap.get(code);
    }

    public Boolean isValidType(Integer code) {
        return codeMap.containsKey(code);
    }

}
