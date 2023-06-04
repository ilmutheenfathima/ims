package com.company.ims.screen.user;

import com.company.ims.entity.User;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("User.detail")
@UiDescriptor("user-detail.xml")
@LookupComponent("usersTable")
public class UserDetail extends StandardLookup<User> {
}