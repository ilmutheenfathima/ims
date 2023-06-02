package com.company.ims.screen.classroom;

import com.company.ims.entity.Classroom;
import com.company.ims.entity.IntakeModule;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@UiController("Classroom.edit")
@UiDescriptor("classroom-edit.xml")
@EditedEntityContainer("classroomDc")
public class ClassroomEdit extends StandardEditor<Classroom> {
    @Autowired
    private CollectionLoader<IntakeModule> intakeModulesDl;
    @Autowired
    private ComboBox<String> dayField;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> daysOfWeek = List.of(
                DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                DayOfWeek.SUNDAY.getDisplayName(TextStyle.FULL, Locale.getDefault())
                );
        dayField.setOptionsList(daysOfWeek);
    }


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        intakeModulesDl.setParameter("intake", getEditedEntity().getBatch().getIntake());
        intakeModulesDl.load();
    }
}