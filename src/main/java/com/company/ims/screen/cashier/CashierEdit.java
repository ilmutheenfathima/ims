package com.company.ims.screen.cashier;

import com.company.ims.entity.User;
import com.company.ims.security.CashierRole;
import com.company.ims.security.DatabaseUserRepository;
import com.company.ims.security.LecturerRole;
import io.jmix.core.EntityStates;
import io.jmix.core.security.event.SingleUserPasswordChangeEvent;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.ims.entity.Cashier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@UiController("Cashier.edit")
@UiDescriptor("cashier-edit.xml")
@EditedEntityContainer("cashierDc")
public class CashierEdit extends StandardEditor<Cashier> {
    @Autowired
    DatabaseUserRepository userRepository;
    @Autowired
    private EntityStates entityStates;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private PasswordField confirmPasswordField;

    @Autowired
    private Notifications notifications;

    @Autowired
    private MessageBundle messageBundle;

    private boolean isNewEntity;

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        usernameField.setEditable(true);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
        isNewEntity = true;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messageBundle.getMessage("passwordsDoNotMatch"))
                        .show();
                event.preventCommit();
            }
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) {
        if (isNewEntity) {
            userRepository.addResourceRoleToUser(getEditedEntity(), CashierRole.CODE); // set cashier role
            getApplicationContext().publishEvent(new SingleUserPasswordChangeEvent(getEditedEntity().getUsername(), passwordField.getValue()));
        }
    }
}