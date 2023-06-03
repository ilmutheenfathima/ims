package com.company.ims.screen.lecturer;

import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.ims.entity.Lecturer;

@Route("lecturers")
@UiController("Lecturer.browse")
@UiDescriptor("lecturer-browse.xml")
@LookupComponent("lecturersTable")
public class LecturerBrowse extends StandardLookup<Lecturer> {
}