package com.example.user.models;

import lombok.Getter;

@Getter
public enum Permission {
    ACCESS_ADMIN("access:admin"),
    ACCESS_USER("access:users");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
