package com.company.ims.screen.intakemodule;

import com.company.ims.entity.Intake;
import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.ModuleContent;
import com.company.ims.screen.modulecontent.ModuleContentEdit;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
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
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private UiComponents uiComponents;

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

    @Install(to = "intakeModulesTable.details", subject = "columnGenerator")
    private Component intakeModulesTableDetailsColumnGenerator(IntakeModule intakeModule) {
        if (intakeModule.getModuleContent() == null) {
            ModuleContent moduleContent = dataManager.create(ModuleContent.class);
            intakeModule.setModuleContent(moduleContent);
            dataManager.save(intakeModule);
        }
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption("Details");
        linkButton.setId(intakeModule.getId().toString());
        linkButton.setStyleName("huge");
        linkButton.setAlignment(Component.Alignment.MIDDLE_LEFT);
        linkButton.addClickListener(clickEvent -> {
            ModuleContentEdit moduleContentEdit = screenBuilders.editor(ModuleContent.class, this)
                    .withScreenClass(ModuleContentEdit.class)
                    .editEntity(intakeModule.getModuleContent())
                    .withOpenMode(OpenMode.DIALOG)
                    .withAfterCloseListener(event -> {
                        intakeModulesDl.load();
                    })
                    .build();
            moduleContentEdit.show();
        });
        return linkButton;
    }
}