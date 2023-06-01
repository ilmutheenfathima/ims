package com.company.ims.screen.lecturer;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Lecturer;

@UiController("Lecturer.edit")
@UiDescriptor("lecturer-edit.xml")
@EditedEntityContainer("lecturerDc")
public class LecturerEdit extends StandardEditor<Lecturer> {
}