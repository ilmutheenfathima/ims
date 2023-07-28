package com.company.ims.screen.enrolledmodules;

import com.company.ims.entity.*;
import com.company.ims.screen.modulecardfragment.ModuleCardFragment;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Fragments;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.Fragment;
import io.jmix.ui.component.ScrollBoxLayout;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("EnrolledModulesScreen")
@UiDescriptor("enrolled-modules-screen.xml")
public class EnrolledModulesScreen extends Screen {

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Fragments fragments;
    @Autowired
    private ScrollBoxLayout cardContainer;
    @Autowired
    private EntityComboBox<Intake> intakeField;
    @Autowired
    private CollectionLoader<Intake> intakesDl;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        intakesDl.load();
        reloadModules();
    }

    private void reloadModules() {
        User user = (User) currentAuthentication.getUser();
        List<IntakeModule> intakeModules = loadModulesForUser(user);
        Fragment[] moduleCardFragments = intakeModules.stream()
                .map(im -> this.createCard(im, user))
                .map(ModuleCardFragment::getFragment)
                .toArray(Fragment[]::new);
        cardContainer.removeAll();
        cardContainer.add(moduleCardFragments);
    }

    private List<IntakeModule> loadModulesForUser(User user) {
        String query = "select distinct e from IntakeModule e";
        String intakeClause = " e.intake = :intake";
        if (user instanceof Lecturer) {
            query = "select distinct e from IntakeModule e\n" +
                    "                    join e.classrooms c\n" +
                    "                    where c.lecturer = :classroomsLecturer ";
            if (intakeField.getValue() != null) {
                return dataManager.load(IntakeModule.class).query(query+ " and " + intakeClause)
                        .parameter("intake", intakeField.getValue())
                        .parameter("classroomsLecturer", user)
                        .fetchPlan("intakeModule-fetch-plan-for-module-page")
                        .list();
            }
            return dataManager.load(IntakeModule.class).query(query)
                    .parameter("classroomsLecturer", user)
                    .fetchPlan("intakeModule-fetch-plan-for-module-page")
                    .list();
        } else if (user instanceof Student) {
            query = "select e from IntakeModule e \n" +
                    "                    join e.enrolments en \n" +
                    "                    where en.student = :enrolledStudent";
            if (intakeField.getValue() != null) {
                return dataManager.load(IntakeModule.class).query(query+ " and " + intakeClause)
                        .parameter("intake", intakeField.getValue())
                        .parameter("enrolledStudent", user)
                        .fetchPlan("intakeModule-fetch-plan-for-module-page")
                        .list();
            }
            return dataManager.load(IntakeModule.class).query(query)
                    .parameter("enrolledStudent", user)
                    .fetchPlan("intakeModule-fetch-plan-for-module-page")
                    .list();
        } else {
            if (intakeField.getValue() != null) {
                return dataManager.load(IntakeModule.class).query(query+ " where " + intakeClause)
                        .parameter("intake", intakeField.getValue())
                        .fetchPlan("intakeModule-fetch-plan-for-module-page")
                        .list();
            }
            return dataManager.load(IntakeModule.class).query(query)
                    .fetchPlan("intakeModule-fetch-plan-for-module-page")
                    .list();
        }
    }

    private ModuleCardFragment createCard(IntakeModule intakeModule, User user) {
        ModuleCardFragment card = fragments.create(this, ModuleCardFragment.class);
        card.setIntakeModule(intakeModule);
        card.setUser(user);
        return card;
    }

    @Subscribe("searchButton")
    public void onSearchButtonClick(Button.ClickEvent event) {
        reloadModules();
    }

    @Install(to = "intakeField", subject = "optionCaptionProvider")
    private String intakeFieldOptionCaptionProvider(Intake intake) {
        return intake.getIntakeLongName();
    }


}