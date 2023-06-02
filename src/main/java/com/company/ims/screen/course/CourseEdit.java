package com.company.ims.screen.course;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Course;

@UiController("Course.edit")
@UiDescriptor("course-edit.xml")
@EditedEntityContainer("courseDc")
public class CourseEdit extends StandardEditor<Course> {
}