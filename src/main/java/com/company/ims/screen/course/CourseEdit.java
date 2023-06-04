package com.company.ims.screen.course;

import com.company.ims.entity.Course;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Course.edit")
@UiDescriptor("course-edit.xml")
@EditedEntityContainer("courseDc")
public class CourseEdit extends StandardEditor<Course> {
}