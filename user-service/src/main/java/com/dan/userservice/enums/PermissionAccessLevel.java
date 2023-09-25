package com.dan.userservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum PermissionAccessLevel {
    ROOT(0, "ROOT"),
    PARENT(1, "PARENT"),
    CHILD(2, "CHILD");


    private final Integer value;
    private final String msg;
    private static final Map<Integer,Object> map = new HashMap();

    PermissionAccessLevel(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (PermissionAccessLevel permissionAccessLevel : PermissionAccessLevel.values()) {
            map.put(permissionAccessLevel.value, permissionAccessLevel);
        }
    }

    public static PermissionAccessLevel valueOf(int permissionAccessLevel) {
        return (PermissionAccessLevel) map.get(permissionAccessLevel);
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

}
