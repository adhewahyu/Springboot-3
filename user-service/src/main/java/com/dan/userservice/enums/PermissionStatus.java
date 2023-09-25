package com.dan.userservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum PermissionStatus {
    ACTIVE(1, "ACTIVE"),
    INACTIVE(0, "INACTIVE");


    private final Integer value;
    private final String msg;
    private static final Map<Integer,Object> map = new HashMap();

    PermissionStatus(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (PermissionStatus permissionStatus : PermissionStatus.values()) {
            map.put(permissionStatus.value, permissionStatus);
        }
    }

    public static PermissionStatus valueOf(int permissionStatus) {
        return (PermissionStatus) map.get(permissionStatus);
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

}
