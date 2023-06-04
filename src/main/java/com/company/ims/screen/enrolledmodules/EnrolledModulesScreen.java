package com.company.ims.screen.enrolledmodules;

import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.Lecturer;
import com.company.ims.entity.Student;
import com.company.ims.entity.User;
import com.company.ims.screen.modulecardfragment.ModuleCardFragment;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Fragments;
import io.jmix.ui.component.Fragment;
import io.jmix.ui.component.ScrollBoxLayout;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
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

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        List<IntakeModule> intakeModules = loadModulesForUser();
        Fragment[] moduleCardFragments = intakeModules.stream()
                .map(this::createCard)
                .map(ModuleCardFragment::getFragment)
                .toArray(Fragment[]::new);
        cardContainer.add(moduleCardFragments);
    }

    private List<IntakeModule> loadModulesForUser() {
        User user = (User) currentAuthentication.getUser();
        String query = "select distinct e from IntakeModule e";
        if (user instanceof Lecturer) {
            query = "select distinct e from IntakeModule e\n" +
                    "                    join e.classrooms c\n" +
                    "                    where c.lecturer = :classroomsLecturer ";
            return dataManager.load(IntakeModule.class).query(query)
                    .parameter("classroomsLecturer", user)
                    .fetchPlan("intakeModule-fetch-plan-for-module-page")
                    .list();
        } else if (user instanceof Student) {
            String studentQuery = "select distinct e from IntakeModule e \n" +
                    "                    join e.classrooms.enrolments en \n" +
                    "                    where e.classrooms.enrolments.student = :classroomsEnrolmentsStudent  ";
            return dataManager.load(IntakeModule.class).query(query)
                    .parameter("classroomsEnrolmentsStudent", user)
                    .fetchPlan("intakeModule-fetch-plan-for-module-page")
                    .list();
        } else {
            return dataManager.load(IntakeModule.class).query(query)
                    .fetchPlan("intakeModule-fetch-plan-for-module-page")
                    .list();
        }
    }

    private ModuleCardFragment createCard(IntakeModule intakeModule) {
        ModuleCardFragment card = fragments.create(this, ModuleCardFragment.class);
        card.setIntakeModule(intakeModule);
        return card;
    }


}