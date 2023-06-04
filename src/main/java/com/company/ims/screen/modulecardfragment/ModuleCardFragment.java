package com.company.ims.screen.modulecardfragment;

import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.ModuleContent;
import com.company.ims.entity.User;
import com.company.ims.screen.modulecontent.ModuleContentEdit;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ModuleCardFragment")
@UiDescriptor("module-card-fragment.xml")
public class ModuleCardFragment extends ScreenFragment {

    private IntakeModule intakeModule;
    private User user;
    @Autowired
    private Label<String> description;
    @Autowired
    private Label<String> title;
    @Autowired
    private Label<String> intake;
    @Autowired
    private Label<String> course;
    @Autowired
    private Label<String> level;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Button editBtn;
    @Autowired
    private DataManager dataManager;

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
        if (user.isAdmin()) {
            editBtn.setEnabled(true);
            editBtn.setVisible(true);
        }
    }

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    @Subscribe("editBtn")
    public void onEditBtnClick(Button.ClickEvent event) {
        ModuleContentEdit paymentBrowse = screenBuilders.editor(ModuleContent.class, this)
                .withScreenClass(ModuleContentEdit.class)
                .editEntity(intakeModule.getModuleContent())
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(closeEvent -> {
                    this.intakeModule = dataManager.load(IntakeModule.class)
                            .id(intakeModule.getId())
                            .fetchPlan("intakeModule-fetch-plan-for-module-page")
                            .one();
                    this.renderComponent();
                })
                .build();
        paymentBrowse.show();
    }

    public void setUser(User user) {
        this.user = user;
    }
}