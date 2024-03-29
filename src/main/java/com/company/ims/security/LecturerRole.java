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
@ResourceRole(name = "LecturerRole", code = LecturerRole.CODE)
public interface LecturerRole extends UiMinimalRole {
    String CODE = "lecturer-role";

    @MenuPolicy(menuIds = {"LecturerHomeScreen", "CalendarScreen", "EnrolledModulesScreen", "EnrolledModulesScreen", "ModulePage",})
    @ScreenPolicy(screenIds = {
            "LecturerHomeScreen",
            "ChangePasswordDialog",
            "User.edit",
            "Lecturer.edit",
            "themeSettingsScreen",
            "CalendarScreen",
            "EnrolledModulesScreen",
            "EnrolledModulesScreen",
            "ModulePage",
            "ContentItem.edit",
            "Classroom.browse",
            "ItemResource.edit",
            "Enrolment.browse",
            "Submission.browse",
            "Submission.edit"
    })
    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void screens();
}



