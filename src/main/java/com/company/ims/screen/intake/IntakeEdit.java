package com.company.ims.screen.intake;

import com.company.ims.entity.Intake;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Intake.edit")
@UiDescriptor("intake-edit.xml")
@EditedEntityContainer("intakeDc")
public class IntakeEdit extends StandardEditor<Intake> {
}