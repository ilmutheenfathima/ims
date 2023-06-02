package com.company.ims.screen.batch;

import com.company.ims.entity.Batch;
import com.company.ims.entity.Intake;
import io.jmix.ui.screen.*;

@UiController("Batch.edit")
@UiDescriptor("batch-edit.xml")
@EditedEntityContainer("batchDc")
public class BatchEdit extends StandardEditor<Batch> {
    @Install(to = "intakeField", subject = "optionCaptionProvider")
    private String intakeFieldOptionCaptionProvider(Intake intake) {
        return intake.getLevel().getLevelLongName() + " " + intake.getName();
    }
}