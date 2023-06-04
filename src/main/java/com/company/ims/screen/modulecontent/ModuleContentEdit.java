package com.company.ims.screen.modulecontent;

import com.company.ims.entity.ModuleContent;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("ModuleContent.edit")
@UiDescriptor("module-content-edit.xml")
@EditedEntityContainer("moduleContentDc")
public class ModuleContentEdit extends StandardEditor<ModuleContent> {
}