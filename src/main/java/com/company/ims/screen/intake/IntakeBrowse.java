package com.company.ims.screen.intake;

import com.company.ims.entity.Intake;
import com.company.ims.screen.intakemodule.IntakeModuleBrowse;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("intakes")
@UiController("Intake.browse")
@UiDescriptor("intake-browse.xml")
@LookupComponent("intakesTable")
public class IntakeBrowse extends StandardLookup<Intake> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Install(to = "intakesTable.action", subject = "columnGenerator")
    private Component intakesTableActionColumnGenerator(Intake intake) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption("Intake Modules");
        linkButton.setId(intake.getId().toString());
        linkButton.setStyleName("huge");
        linkButton.setAlignment(Component.Alignment.MIDDLE_LEFT);
        linkButton.addClickListener(clickEvent -> {
            IntakeModuleBrowse intakeModuleBrowse = screenBuilders.screen(this)
                    .withScreenClass(IntakeModuleBrowse.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .build();
            intakeModuleBrowse.setIntake(intake);
            intakeModuleBrowse.show();
        });
        return linkButton;
    }
}