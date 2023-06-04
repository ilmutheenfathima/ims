package com.company.ims.screen.student;

import com.company.ims.entity.Enrolment;
import com.company.ims.entity.Student;
import com.company.ims.entity.User;
import com.company.ims.security.DatabaseUserRepository;
import com.company.ims.security.StudentRole;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.security.event.SingleUserPasswordChangeEvent;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@UiController("Student.edit")
@UiDescriptor("student-edit.xml")
@EditedEntityContainer("studentDc")
public class StudentEdit extends StandardEditor<Student> {
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
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionLoader<Enrolment> enrolmentsDl;

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
        } else {
            usernameField.setEditable(false);
        }
        enrolmentsDl.setParameter("student", getEditedEntity());
        enrolmentsDl.load();
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
            userRepository.addResourceRoleToUser(getEditedEntity(), StudentRole.CODE); // set student role
            getApplicationContext().publishEvent(
                    new SingleUserPasswordChangeEvent(getEditedEntity().getUsername(),
                            passwordField.getValue()
                    )
            );
        }
    }

    @Install(to = "enrolmentsTable.create", subject = "newEntitySupplier")
    private Enrolment enrolmentsTableCreateNewEntitySupplier() {
        Enrolment enrolment = dataManager.create(Enrolment.class);
        enrolment.setStudent(getEditedEntity());
        return enrolment;
    }
}