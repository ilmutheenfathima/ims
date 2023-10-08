package com.company.ims.screen.lecturer;

import com.company.ims.entity.Lecturer;
import com.company.ims.entity.Module;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("lecturers")
@UiController("Lecturer.browse")
@UiDescriptor("lecturer-browse.xml")
@LookupComponent("lecturersTable")
public class LecturerBrowse extends StandardLookup<Lecturer> {
    @Autowired
    private CollectionLoader<Lecturer> lecturersDl;
    @Autowired
    private EntityComboBox<Module> moduleField;

    @Subscribe("searchButton")
    public void onSearchButtonClick(Button.ClickEvent event) {
        if (moduleField.getValue() != null) {
            lecturersDl.setQuery("select e from Lecturer e where e.module = :module");
            lecturersDl.setParameter("module", moduleField.getValue());
            lecturersDl.load();
        } else {
            lecturersDl.removeParameter("module");
            lecturersDl.setQuery("select e from Lecturer e");
            lecturersDl.load();
        }
    }
}