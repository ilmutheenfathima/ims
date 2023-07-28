package com.company.ims.screen.course;

import com.company.ims.entity.Course;
import com.company.ims.entity.Level;
import io.jmix.ui.screen.*;

@UiController("Course.edit")
@UiDescriptor("course-edit.xml")
@EditedEntityContainer("courseDc")
public class CourseEdit extends StandardEditor<Course> {
//    @Subscribe
//    public void onAfterShow(AfterShowEvent event) {
//        String captionName = getEditedEntity().getName();
//        captionName = (captionName != null) ? captionName : ""; // Replaces null with an empty string
//        if (captionName == null) {
//            captionName = "";
//        }
//        getWindow().setCaption("Edit Course - " + captionName);
//    }
}