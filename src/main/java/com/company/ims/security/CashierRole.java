package com.company.ims.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

import javax.annotation.Nonnull;

@Nonnull
@ResourceRole(name = "Cashier", code = CashierRole.CODE)
public interface CashierRole extends UiMinimalRole {
    String CODE = "cashier-role";

    @MenuPolicy(menuIds = {"CashierHomeScreen", "CalendarScreen", "EnrolledModulesScreen"})
    @ScreenPolicy(screenIds = {"CashierHomeScreen", "CalendarScreen", "EnrolledModulesScreen"})
    void screens();
}