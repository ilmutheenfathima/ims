package com.company.ims.screen.module;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Module;

@UiController("Module_.edit")
@UiDescriptor("module-edit.xml")
@EditedEntityContainer("moduleDc")
public class ModuleEdit extends StandardEditor<Module> {
}