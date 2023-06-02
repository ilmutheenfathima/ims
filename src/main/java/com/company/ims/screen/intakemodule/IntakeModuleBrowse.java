package com.company.ims.screen.intakemodule;

import com.company.ims.entity.Intake;
import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.ModuleContent;
import io.jmix.core.DataManager;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("IntakeModule.browse")
@UiDescriptor("intake-module-browse.xml")
@LookupComponent("intakeModulesTable")
public class IntakeModuleBrowse extends StandardLookup<IntakeModule> {

    private Intake intake;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CollectionLoader<IntakeModule> intakeModulesDl;

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Intake Modules of " + intake.getName());
        intakeModulesDl.setParameter("intake", intake);
        intakeModulesDl.load();
    }


    @Install(to = "intakeModulesTable.create", subject = "newEntitySupplier")
    private IntakeModule intakeModulesTableCreateNewEntitySupplier() {
        IntakeModule intakeModule = dataManager.create(IntakeModule.class);
        intakeModule.setModuleContent(dataManager.create(ModuleContent.class));
        intakeModule.setIntake(intake);
        return intakeModule;
    }
}