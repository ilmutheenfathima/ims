package com.company.ims.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

import javax.annotation.Nonnull;

@Nonnull
@ResourceRole(name = "StudentRole", code = StudentRole.CODE)
public interface StudentRole extends UiMinimalRole {

    String CODE = "student-role";


    @MenuPolicy(menuIds = "StudentHomeScreen")
    @ScreenPolicy(screenIds = "StudentHomeScreen")
    void screens();
}