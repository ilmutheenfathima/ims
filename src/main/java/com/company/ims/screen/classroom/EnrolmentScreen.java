package com.company.ims.screen.classroom;

import com.company.ims.entity.Classroom;
import com.company.ims.screen.enrolment.EnrolmentBrowse;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Enrolment.screen")
@UiDescriptor("enrolment-screen.xml")
@LookupComponent("classroomsTable")
public class EnrolmentScreen extends StandardLookup<Classroom> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Install(to = "classroomsTable.enrolledCount", subject = "columnGenerator")
    private Component classroomsTableEnrolledCountColumnGenerator(Classroom classroom) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption(classroom.getEnrolments().size() + " Enrolments");
        linkButton.setId(classroom.getId().toString());
        linkButton.setStyleName("huge");
        linkButton.setAlignment(Component.Alignment.MIDDLE_LEFT);
        linkButton.addClickListener(clickEvent -> {
            EnrolmentBrowse enrolmentBrowse = screenBuilders.screen(this)
                    .withScreenClass(EnrolmentBrowse.class)
                    .withOpenMode(OpenMode.THIS_TAB)
                    .build();
            enrolmentBrowse.setClassroom(classroom);
            enrolmentBrowse.show();
        });
        return linkButton;
    }
}