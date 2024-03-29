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
@ResourceRole(name = "Cashier", code = CashierRole.CODE)
public interface CashierRole extends UiMinimalRole {
    String CODE = "cashier-role";

    @MenuPolicy(menuIds = {"CashierHomeScreen", "Enrolment.screen"})
    @ScreenPolicy(screenIds = {"CashierHomeScreen","Enrolment.screen", "Enrolment.browse","Payment.browse", "Payment.edit"})
    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)

    void screens();

}