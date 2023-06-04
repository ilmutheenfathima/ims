package com.company.ims.exception;

import io.jmix.ui.Notifications;
import io.jmix.ui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component("uiex1_SQLIntegrityConstraintViolationExceptionHandler")
public class SQLIntegrityConstraintViolationExceptionHandler extends AbstractUiExceptionHandler {
    public SQLIntegrityConstraintViolationExceptionHandler() {
        super("java.sql.SQLIntegrityConstraintViolationException");
    }

    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {
        String messageFormatted = "This record cannot delete while having child records";
        if (message.contains("REFERENCES `COURSE`")) {
            messageFormatted = "Course cannot delete while having child records";
        }
        if (message.contains("REFERENCES `LEVEL_`")) {
            messageFormatted = "Level cannot delete while having child records";
        }
        context.getNotifications().create(Notifications.NotificationType.ERROR)
                .withCaption("Error")
                .withDescription(messageFormatted)
                .show();
    }
}
