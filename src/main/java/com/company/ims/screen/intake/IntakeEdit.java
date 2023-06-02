package com.company.ims.screen.intake;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Intake;

@UiController("Intake.edit")
@UiDescriptor("intake-edit.xml")
@EditedEntityContainer("intakeDc")
public class IntakeEdit extends StandardEditor<Intake> {
}