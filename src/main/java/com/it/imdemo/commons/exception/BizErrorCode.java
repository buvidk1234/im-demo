package com.it.imdemo.commons.exception;

public enum BizErrorCode {

    // 通用错误
    INVALID_PARAM("COMMON_001", "参数不合法"),
    SYSTEM_ERROR("COMMON_999", "系统异常"),

    // 用户相关
    USER_NOT_FOUND("USER_001", "用户不存在"),
    USER_DISABLED("USER_002", "用户已被禁用"),
    USER_ALREADY_EXISTS("USER_003", "用户已存在"),

    // 认证&授权
    INVALID_CREDENTIALS("AUTH_001", "用户名或密码错误"),
    TOKEN_EXPIRED("AUTH_002", "登录已过期"),
    ACCESS_DENIED("AUTH_003", "没有权限执行该操作"),
    UNAUTHENTICATED("AUTH_004", "用户未认证"),

    // 群组 / 业务相关（结合你前面的 ChatGroup 例子）
    GROUP_NOT_FOUND("GROUP_001", "群组不存在"),
    GROUP_MEMBER_ALREADY_EXISTS("GROUP_002", "用户已是群成员"),
    GROUP_MEMBER_LIMIT_REACHED("GROUP_003", "群成员数量已达上限"),
    GROUP_OPERATOR_FORBIDDEN("GROUP_004", "无权操作该群组"),

    // 好友相关
    FRIENDSHIP_NOT_FOUND("FRIEND_001", "好友关系不存在"),
    FRIENDSHIP_ALREADY_EXISTS("FRIEND_002", "好友关系已存在"),
    FRIENDSHIP_CANNOT_ADD_SELF("FRIEND_003", "不能添加自己为好友"),
    FRIENDSHIP_NOT_REQUESTED("FRIEND_004", "好友请求不存在");

    private final String code;
    private final String message;

    BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
