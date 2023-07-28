package com.company.ims.screen.intake;

import com.company.ims.entity.Course;
import com.company.ims.entity.Intake;
import com.company.ims.entity.Level;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Intake.edit")
@UiDescriptor("intake-edit.xml")
@EditedEntityContainer("intakeDc")
public class IntakeEdit extends StandardEditor<Intake> {
    @Autowired
    private EntityComboBox<Course> courseField;
    @Autowired
    private EntityComboBox<Level> levelField;
    @Autowired
    private CollectionLoader<Level> levelsDl;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Intake intake = getEditedEntity();
        if (intake.getLevel() != null) {
            courseField.setValue(intake.getLevel().getCourse());
            levelsDl.setParameter("course", intake.getLevel().getCourse());
            levelsDl.load();
        } else {
            levelField.setEnabled(false);
        }
    }

    @Subscribe("courseField")
    public void onCourseFieldValueChange(HasValue.ValueChangeEvent<Course> event) {
        if (event.getValue() != null) {
            levelField.setEnabled(true);
            levelsDl.setParameter("course", event.getValue());
            levelsDl.load();
        } else {
            levelField.setEnabled(false);
        }
        levelField.setValue(null);
    }
}