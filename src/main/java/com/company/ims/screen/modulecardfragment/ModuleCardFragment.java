package com.company.ims.screen.modulecardfragment;

import com.company.ims.entity.Classroom;
import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.ModuleContent;
import com.company.ims.entity.User;
import com.company.ims.screen.enrolment.EnrolmentBrowse;
import com.company.ims.screen.modulecontent.ModuleContentEdit;
import com.company.ims.screen.modulepage.ModulePage;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.screen.*;
import io.jmix.ui.theme.ThemeClassNames;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private VBoxLayout classroomBtnPanel;
    @Autowired
    private UiComponents uiComponents;

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
        renderClassrooms();
    }

    private void renderClassrooms() {
        if (user.isStudent()) {
            classroomBtnPanel.setVisible(false);
            return;
        }

        List<Classroom> classroomList = new ArrayList<>();
        if (user.isAdmin() || user.isCashier()) {
            classroomList = this.intakeModule.getClassrooms() != null ? this.intakeModule.getClassrooms() : classroomList;
        } else if (user.isLecturer()) {
            if (this.intakeModule.getClassrooms() != null) {
                classroomList = this.intakeModule.getClassrooms().stream()
                        .filter(c -> c.getLecturer().getId().equals(user.getId()))
                        .collect(Collectors.toList());
            }
        }

        List<Button> classButtons = new ArrayList<>();
        for (Classroom classroom : classroomList) {
            Button button = uiComponents.create(Button.class);
            button.setCaption(classroom.getBatch().getName());
            button.setStyleName(ThemeClassNames.BUTTON_FRIENDLY);
            button.setAlignment(Component.Alignment.MIDDLE_CENTER);
            button.addClickListener(clickEvent -> {
                EnrolmentBrowse enrolmentBrowse = screenBuilders.screen(this)
                        .withScreenClass(EnrolmentBrowse.class)
                        .withOpenMode(OpenMode.THIS_TAB)
                        .build();
                enrolmentBrowse.setClassroom(classroom);
                enrolmentBrowse.show();
            });
            classButtons.add(button);
        }
        classroomBtnPanel.setVisible(true);
        classroomBtnPanel.removeAll();
        classroomBtnPanel.add(classButtons.toArray(new Button[]{}));
    }

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    @Subscribe("editBtn")
    public void onEditBtnClick(Button.ClickEvent event) {
        ModuleContentEdit moduleContentEdit = screenBuilders.editor(ModuleContent.class, this)
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
        moduleContentEdit.show();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Subscribe("viewModuleContentBtn")
    public void onViewModuleContentBtnClick(Button.ClickEvent event) {
        ModulePage modulePage = screenBuilders.screen(this)
                .withScreenClass(ModulePage.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .build();
        modulePage.setIntakeModule(intakeModule);
        modulePage.setUser(user);
        modulePage.show();
    }
}