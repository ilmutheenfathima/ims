package com.company.ims.screen.course;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.ims.entity.Course;

@Route("courses")
@UiController("Course.browse")
@UiDescriptor("course-browse.xml")
@LookupComponent("coursesTable")
public class CourseBrowse extends StandardLookup<Course> {
}