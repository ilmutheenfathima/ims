package com.company.ims.screen.student;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Student;

@UiController("Student.edit")
@UiDescriptor("student-edit.xml")
@EditedEntityContainer("studentDc")
public class StudentEdit extends StandardEditor<Student> {
}