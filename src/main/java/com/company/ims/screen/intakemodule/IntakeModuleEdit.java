package com.company.ims.screen.intakemodule;

import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.Module;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("IntakeModule.edit")
@UiDescriptor("intake-module-edit.xml")
@EditedEntityContainer("intakeModuleDc")
public class IntakeModuleEdit extends StandardEditor<IntakeModule> {
    @Autowired
    private CollectionLoader<Module> modulesDl;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        modulesDl.setParameter("level", getEditedEntity().getIntake().getLevel());

        // remove alredy added modules from option list loading data loader
        List<UUID> alreadyAddedModuleIds = new ArrayList<>();
        if (getEditedEntity().getIntake().getIntakeModules() != null) {
            alreadyAddedModuleIds = getEditedEntity().getIntake().getIntakeModules().stream()
                    .map(intakeModule -> intakeModule.getModule().getId())
                    .collect(Collectors.toList());
        }
        modulesDl.setParameter("alreadyAddedModules", alreadyAddedModuleIds);

        modulesDl.load();
    }

}