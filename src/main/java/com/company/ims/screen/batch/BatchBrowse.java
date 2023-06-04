package com.company.ims.screen.batch;

import com.company.ims.entity.Batch;
import com.company.ims.screen.classroom.ClassroomBrowse;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("batches")
@UiController("Batch.browse")
@UiDescriptor("batch-browse.xml")
@LookupComponent("batchesTable")
public class BatchBrowse extends StandardLookup<Batch> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Install(to = "batchesTable.action", subject = "columnGenerator")
    private Component batchesTableActionColumnGenerator(Batch batch) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption("Classrooms");
        linkButton.setId(batch.getId().toString());
        linkButton.setStyleName("huge");
        linkButton.setAlignment(Component.Alignment.MIDDLE_LEFT);
        linkButton.addClickListener(clickEvent -> {
            ClassroomBrowse classroomBrowse = screenBuilders.screen(this)
                    .withScreenClass(ClassroomBrowse.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .build();
            classroomBrowse.setBatch(batch);
            classroomBrowse.show();
        });
        return linkButton;
    }
}