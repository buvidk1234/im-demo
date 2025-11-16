package com.it.imdemo.domain.user.model;

import lombok.Getter;

@Getter
public enum OnlineStatus {

    OFFLINE(0),
    ONLINE(1),
    AWAY(2);

    private final int code;

    OnlineStatus(int code) {
        this.code = code;
    }

    public static OnlineStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        return switch (code) {
            case 0 -> OFFLINE;
            case 1 -> ONLINE;
            case 2 -> AWAY;
            default -> null; // 或者抛异常
        };
    }
}
