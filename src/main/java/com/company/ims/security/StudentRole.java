package com.company.ims.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

import javax.annotation.Nonnull;

@Nonnull
@ResourceRole(name = "StudentRole", code = StudentRole.CODE)
public interface StudentRole extends UiMinimalRole {

    String CODE = "student-role";

    @MenuPolicy(menuIds = {"StudentHomeScreen", "CalendarScreen", "EnrolledModulesScreen", "ModulePage"})
    @ScreenPolicy(screenIds = {
            "StudentHomeScreen",
            "ChangePasswordDialog",
            "Student.edit",
            "themeSettingsScreen",
            "CalendarScreen",
            "EnrolledModulesScreen",
            "ModulePage",
            "Submission.edit"
    })
    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void screens();
}