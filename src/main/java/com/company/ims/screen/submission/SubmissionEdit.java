package com.company.ims.screen.submission;

import com.company.ims.entity.Submission;
import com.company.ims.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.FileStorageUploadField;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Submission.edit")
@UiDescriptor("submission-edit.xml")
@EditedEntityContainer("submissionDc")
public class SubmissionEdit extends StandardEditor<Submission> {
    private User user;
    @Autowired
    private TextArea<String> textField;
    @Autowired
    private FileStorageUploadField fileField;
    @Autowired
    private TextField<Float> marksField;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    public void setUser(User user) {
        this.user = user;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        user = (User) currentAuthentication.getUser();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (user.isStudent()) {
            marksField.setEnabled(false);
            marksField.setVisible(false);
        } else if (user.isLecturer()) {
            marksField.setVisible(true);
            marksField.setEnabled(true);
            textField.setEnabled(true);
            fileField.setEnabled(true);
            textField.setEditable(false);
            fileField.setEditable(false);
        }
    }


}