package com.company.ims.screen.enrolment;

import com.company.ims.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@UiController("Enrolment.edit")
@UiDescriptor("enrolment-edit.xml")
@EditedEntityContainer("enrolmentDc")
public class EnrolmentEdit extends StandardEditor<Enrolment> {
    @Autowired
    private EntityComboBox<Level> levelSelection;
    @Autowired
    private EntityComboBox<Intake> intakeSelection;
    @Autowired
    private EntityComboBox<IntakeModule> intakeModuleSelection;
    @Autowired
    private CollectionLoader<Intake> intakesDl;
    @Autowired
    private CollectionLoader<Level> levelsDl;
    @Autowired
    private CollectionLoader<IntakeModule> intakeModulesDl;
    @Autowired
    private CollectionLoader<Classroom> classroomsDl;
    @Autowired
    private Action windowCommitAndClose;
    @Autowired
    private EntityComboBox<Classroom> classroomSelection;
    @Autowired
    private EntityComboBox<Course> courseSelection;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Enrolment enrolment = getEditedEntity();
        if (enrolment.getIntakeModule() != null) {
            courseSelection.setValue(enrolment.getIntakeModule().getIntake().getLevel().getCourse());
            levelSelection.setValue(enrolment.getIntakeModule().getIntake().getLevel());
            intakeSelection.setValue(enrolment.getIntakeModule().getIntake());
            intakeModuleSelection.setValue(enrolment.getIntakeModule());

            // not letting change enrolments data, but letting change the classroom
            courseSelection.setEnabled(false);
            levelSelection.setEnabled(false);
            intakeSelection.setEnabled(false);
            intakeModuleSelection.setEnabled(false);
            classroomsDl.setParameter("intakeModule", enrolment.getIntakeModule());
            classroomSelection.setEnabled(true);
        }
        if (enrolment.getClassroom() != null) {
            classroomSelection.setValue(enrolment.getClassroom());
            classroomSelection.setEnabled(true);
        }
    }


    @Subscribe("courseSelection")
    public void onCourseSelectionValueChange(HasValue.ValueChangeEvent<Course> event) {
        if (event.getValue() != null) {
            levelSelection.setEnabled(true);
            levelsDl.setParameter("course", event.getValue());
            levelsDl.load();
        } else {
            levelSelection.setEnabled(false);
            intakeSelection.setEnabled(false);
            intakeModuleSelection.setEnabled(false);
            classroomSelection.setEnabled(false);
        }
        levelSelection.setValue(null);
        intakeSelection.setValue(null);
        intakeModuleSelection.setValue(null);
        classroomSelection.setValue(null);
    }

    @Subscribe("levelSelection")
    public void onLevelSelectionValueChange(HasValue.ValueChangeEvent<Level> event) {
        if (event.getValue() != null) {
            intakeSelection.setEnabled(true);
            intakesDl.setParameter("level", event.getValue());
            intakesDl.load();
        } else {
            intakeSelection.setEnabled(false);
            intakeModuleSelection.setEnabled(false);
            classroomSelection.setEnabled(false);
        }
        intakeSelection.setValue(null);
        intakeModuleSelection.setValue(null);
        classroomSelection.setValue(null);
    }

    @Subscribe("intakeSelection")
    public void onIntakeSelectionValueChange(HasValue.ValueChangeEvent<Intake> event) {
        if (event.getValue() != null) {
            intakeModuleSelection.setEnabled(true);
            intakeModulesDl.setParameter("intake", event.getValue());
            intakeModulesDl.load();
        } else {
            intakeModuleSelection.setEnabled(false);
            classroomSelection.setEnabled(false);
        }
        intakeModuleSelection.setValue(null);
        classroomSelection.setValue(null);
    }

    @Subscribe("intakeModuleSelection")
    public void onIntakeModuleSelectionValueChange(HasValue.ValueChangeEvent<IntakeModule> event) {
        if (event.getValue() != null) {
            classroomSelection.setEnabled(true);
            getEditedEntity().setIntakeModule(event.getValue());
            classroomsDl.setParameter("intakeModule", event.getValue());
            classroomsDl.load();
        } else {
            classroomSelection.setEnabled(false);
        }
    }

    @Subscribe("classroomSelection")
    public void onClassroomSelectionValueChange(HasValue.ValueChangeEvent<Classroom> event) {
        if (event.getValue() != null) {
            getEditedEntity().setClassroom(event.getValue());
            windowCommitAndClose.setEnabled(true);
        } else {
            windowCommitAndClose.setEnabled(false);
        }
    }

    @Install(to = "classroomSelection", subject = "optionCaptionProvider")
    private String classroomSelectionOptionCaptionProvider(Classroom classroom) {
        return classroom.getIdentifiableName();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        Enrolment enrolment = getEditedEntity();
        List<Enrolment> studentEnrolments = dataManager.load(Enrolment.class).condition(
                PropertyCondition.equal("student", enrolment.getStudent())
        ).list();
        Optional<Enrolment> sameModuleEnrolled = studentEnrolments.stream()
                .filter(e -> e.getIntakeModule().getModule().getId().equals(enrolment.getIntakeModule().getModule().getId()))
                .findAny();
        if (sameModuleEnrolled.isPresent()) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messageBundle.getMessage("moduleAlreadyEnrolledMsg"))
                    .show();
            event.preventCommit();
        }
    }

}