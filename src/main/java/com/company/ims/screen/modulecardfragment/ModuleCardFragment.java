package com.company.ims.screen.modulecardfragment;

import com.company.ims.entity.IntakeModule;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ModuleCardFragment")
@UiDescriptor("module-card-fragment.xml")
public class ModuleCardFragment extends ScreenFragment {

    private IntakeModule intakeModule;
    @Autowired
    private Label<String> description;
    @Autowired
    private Label<String> title;

    @Subscribe
    public void onInit(InitEvent event) {
        title.setValue(intakeModule.getModuleName());
        description.setValue(intakeModule.getModuleContent().getDescription());
    }

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }
}