package com.company.ims.screen.module;

import com.company.ims.entity.Module;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Module_.edit")
@UiDescriptor("module-edit.xml")
@EditedEntityContainer("moduleDc")
public class ModuleEdit extends StandardEditor<Module> {
}