package com.company.ims.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

import javax.annotation.Nonnull;

@Nonnull
@ResourceRole(name = "LecturerRole", code = LecturerRole.CODE)
public interface LecturerRole {
    String CODE = "lecturer-role";

    @MenuPolicy(menuIds = "LecturerHomeScreen")
    @ScreenPolicy(screenIds = "LecturerHomeScreen")
    void screens();
}



