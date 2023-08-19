package com.dan.userservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum RoleStatus {
    NEW(0, "NEW"),
    ACTIVE(1, "ACTIVE"),
    INACTIVE(2, "INACTIVE"),
    DELETED(3, "DELETED");


    private final Integer value;
    private final String msg;
    private static final Map<Integer,Object> map = new HashMap();

    RoleStatus(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (RoleStatus roleStatus : RoleStatus.values()) {
            map.put(roleStatus.value, roleStatus);
        }
    }

    public static RoleStatus valueOf(int roleStatus) {
        return (RoleStatus) map.get(roleStatus);
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
