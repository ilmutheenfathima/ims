package com.company.ims.security;

import io.jmix.core.security.CurrentAuthentication;
import io.jmix.securityui.screen.changepassword.ChangePasswordDialog;
import io.jmix.ui.AppUI;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.screen.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("changeUserPasswordMenuBean")
public class ChangeUserPasswordMenu {
    @Autowired
    private ScreenBuilders screenBuilders;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    public void showChangePwdDialog() {
        final Screen frameOwner = Objects.requireNonNull(AppUI.getCurrent()).getTopLevelWindowNN().getFrameOwner();

        screenBuilders.screen(frameOwner)
                .withScreenClass(ChangePasswordDialog.class)
                .build()
                .withUsername(currentAuthentication.getUser().getUsername())
                .withCurrentPasswordRequired(true)
                .show();
    }

}
