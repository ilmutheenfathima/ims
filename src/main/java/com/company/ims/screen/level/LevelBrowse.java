package com.company.ims.screen.level;

import com.company.ims.entity.Level;
import com.company.ims.screen.module.ModuleBrowse;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.component.Table;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("levels")
@UiController("Level_.browse")
@UiDescriptor("level-browse.xml")
@LookupComponent("levelsTable")
public class LevelBrowse extends StandardLookup<Level> {
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UiComponents uiComponents;

//    @Subscribe("levelsTable.action")
//    public void onLevelsTableActionClick(Table.Column.ClickEvent<Level> event) {
//        ModuleBrowse moduleBrowse = screenBuilders.screen(this)
//                .withScreenClass(ModuleBrowse.class)
//                .withOpenMode(OpenMode.THIS_TAB)
//                .build();
//        moduleBrowse.setLevel(event.getItem());
//        moduleBrowse.show();
//    }

    @Install(to = "levelsTable.action", subject = "columnGenerator")
    private Component levelsTableActionColumnGenerator(Level level) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption("Modules");
        linkButton.setId(level.getId().toString());
        linkButton.setStyleName("huge");
        linkButton.setAlignment(Component.Alignment.MIDDLE_LEFT);
        linkButton.addClickListener(clickEvent -> {
            ModuleBrowse moduleBrowse = screenBuilders.screen(this)
                    .withScreenClass(ModuleBrowse.class)
                    .withOpenMode(OpenMode.THIS_TAB)
                    .build();
            moduleBrowse.setLevel(level);
            moduleBrowse.show();
        });
        return linkButton;
    }


}