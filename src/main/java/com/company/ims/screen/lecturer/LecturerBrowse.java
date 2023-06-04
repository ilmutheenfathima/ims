package com.company.ims.screen.lecturer;

import com.company.ims.entity.Lecturer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@Route("lecturers")
@UiController("Lecturer.browse")
@UiDescriptor("lecturer-browse.xml")
@LookupComponent("lecturersTable")
public class LecturerBrowse extends StandardLookup<Lecturer> {
}