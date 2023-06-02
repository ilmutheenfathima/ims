package com.company.ims.screen.level;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Level;

@UiController("Level_.edit")
@UiDescriptor("level-edit.xml")
@EditedEntityContainer("levelDc")
public class LevelEdit extends StandardEditor<Level> {
}