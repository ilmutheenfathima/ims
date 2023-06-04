package com.company.ims.screen.modulecontent;

import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.User;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ModuleContent")
@UiDescriptor("module-content.xml")
public class ModuleContent extends ScreenFragment {
    private IntakeModule intakeModule;
    private User user;
    @Autowired
    private Label<String> course;
    @Autowired
    private Label<String> intake;
    @Autowired
    private Label<String> level;
    @Autowired
    private Label<String> title;
    @Autowired
    private Label<String> description;

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        renderComponent();
    }



    private void renderComponent() {
        title.setValue(intakeModule.getModuleName());
        description.setValue(intakeModule.getModuleContent().getDescription());
        course.setValue(intakeModule.getIntake().getLevel().getCourse().getName() + " >");
        level.setValue(" " + intakeModule.getIntake().getLevel().getName() + " >");
        intake.setValue(" " + intakeModule.getIntake().getName());
    }
}