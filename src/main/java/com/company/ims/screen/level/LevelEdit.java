package com.company.ims.screen.level;

import com.company.ims.entity.Level;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Level_.edit")
@UiDescriptor("level-edit.xml")
@EditedEntityContainer("levelDc")
public class LevelEdit extends StandardEditor<Level> {
}