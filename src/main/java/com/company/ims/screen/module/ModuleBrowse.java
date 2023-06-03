package com.company.ims.screen.module;

import com.company.ims.entity.Level;
import com.company.ims.entity.Module;
import io.jmix.core.DataManager;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("modules")
@UiController("Module_.browse")
@UiDescriptor("module-browse.xml")
@LookupComponent("modulesTable")
public class ModuleBrowse extends StandardLookup<Module> {
    @Autowired
    private CollectionLoader<Module> modulesDl;
    private Level level;
    @Autowired
    private DataManager dataManager;
    public void setLevel(Level level) {
        this.level = level;
    }
    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Modules of " + level.getLevelLongName());
        modulesDl.setParameter("level", level);
        modulesDl.load();
    }

    @Install(to = "modulesTable.create", subject = "newEntitySupplier")
    private Module modulesTableCreateNewEntitySupplier() {
        Module module = dataManager.create(Module.class);
        module.setLevel(this.level);
        return module;
    }
}