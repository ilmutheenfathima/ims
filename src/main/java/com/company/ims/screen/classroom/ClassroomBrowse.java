package com.company.ims.screen.classroom;

import com.company.ims.entity.Batch;
import com.company.ims.entity.Classroom;
import io.jmix.core.DataManager;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Classroom.browse")
@UiDescriptor("classroom-browse.xml")
@LookupComponent("classroomsTable")
public class ClassroomBrowse extends StandardLookup<Classroom> {

    private Batch batch;
    @Autowired
    private CollectionLoader<Classroom> classroomsDl;
    @Autowired
    private DataManager dataManager;

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Classrooms of " + batch.getIntake().getLevel().getLevelLongName() + " - " + batch.getIntake().getName() + " - " + batch.getName());
        classroomsDl.setParameter("batch", batch);
        classroomsDl.load();
    }

    @Install(to = "classroomsTable.create", subject = "newEntitySupplier")
    private Classroom classroomsTableCreateNewEntitySupplier() {
        Classroom classroom = dataManager.create(Classroom.class);
        classroom.setBatch(batch);
        return classroom;
    }


}