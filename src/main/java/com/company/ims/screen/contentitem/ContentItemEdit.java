package com.company.ims.screen.contentitem;

import com.company.ims.entity.ContentItem;
import com.company.ims.screen.classroom.ClassroomBrowse;
import io.jmix.ui.screen.*;

@UiController("ContentItem.edit")
@UiDescriptor("content-item-edit.xml")
@EditedEntityContainer("contentItemDc")
public class ContentItemEdit extends StandardEditor<ContentItem> {
    @Install(to = "visibleClassroomsTable.add", subject = "screenConfigurer")
    private void visibleClassroomsTableAddScreenConfigurer(Screen screen) {
        ClassroomBrowse classroomBrowse = (ClassroomBrowse) screen;
        classroomBrowse.setIntakeModule(getEditedEntity().getContent().getIntakeModule());
    }
}