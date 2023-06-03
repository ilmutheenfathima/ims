package com.company.ims.screen.user;

import io.jmix.ui.screen.*;
import com.company.ims.entity.User;

@UiController("User.detail")
@UiDescriptor("user-detail.xml")
@LookupComponent("usersTable")
public class UserDetail extends StandardLookup<User> {
}