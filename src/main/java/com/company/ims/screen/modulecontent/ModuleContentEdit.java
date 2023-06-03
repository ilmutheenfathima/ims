package com.company.ims.screen.modulecontent;

import io.jmix.ui.screen.*;
import com.company.ims.entity.ModuleContent;

@UiController("ModuleContent.edit")
@UiDescriptor("module-content-edit.xml")
@EditedEntityContainer("moduleContentDc")
public class ModuleContentEdit extends StandardEditor<ModuleContent> {
}