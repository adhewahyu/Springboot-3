package com.dan.userservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
    NEW(0, "NEW"),
    ACTIVE(1, "ACTIVE"),
    INACTIVE(2, "INACTIVE"),
    DELETED(3, "DELETED");


    private final Integer value;
    private final String msg;
    private static final Map<Integer,Object> map = new HashMap();

    UserStatus(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (UserStatus taskStatus : UserStatus.values()) {
            map.put(taskStatus.value, taskStatus);
        }
    }

    public static UserStatus valueOf(int taskStatus) {
        return (UserStatus) map.get(taskStatus);
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
