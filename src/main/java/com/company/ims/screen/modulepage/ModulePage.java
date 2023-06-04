package com.company.ims.screen.modulepage;

import com.company.ims.entity.ContentItem;
import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.User;
import com.company.ims.screen.contentitem.ContentItemEdit;
import com.company.ims.screen.contentitem.ContentItemFragment;
import com.company.ims.screen.modulecontent.ModuleContent;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Fragments;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Fragment;
import io.jmix.ui.component.ScrollBoxLayout;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("ModulePage")
@UiDescriptor("module-page.xml")
public class ModulePage extends Screen {
    private IntakeModule intakeModule;
    private User user;
    @Autowired
    private ScrollBoxLayout cardContainer;
    @Autowired
    private Fragments fragments;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Button createBtn;


    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (user.isLecturer()) {
            createBtn.setVisible(true);
            createBtn.setEnabled(true);
        }
        renderPage();
    }

    private void renderPage() {
        cardContainer.removeAll();
        renderItems();
    }

    private void renderItems() {
        cardContainer.removeAll();
        ModuleContent moduleContent = fragments.create(this, ModuleContent.class);
        moduleContent.setIntakeModule(intakeModule);
        moduleContent.setUser(user);

        cardContainer.add(moduleContent.getFragment());
        List<ContentItem> contentItems = dataManager.load(ContentItem.class)
                .condition(PropertyCondition.equal("content", intakeModule.getModuleContent()))
                .fetchPlan("contentItem-fetch-plan-for-items-page")
                .list();
        Fragment[] contentItemFragments = contentItems.stream()
                .map(ci -> createCard(ci, user))
                .map(ContentItemFragment::getFragment)
                .toArray(Fragment[]::new);
        cardContainer.add(contentItemFragments);
    }

    private ContentItemFragment createCard(ContentItem contentItem, User user) {
        ContentItemFragment card = fragments.create(this, ContentItemFragment.class);
        card.setContentItem(contentItem);
        card.setUser(user);
        return card;
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        // create content item box
        ContentItem contentItem = dataManager.create(ContentItem.class);
        contentItem.setContent(intakeModule.getModuleContent());
        ContentItemEdit moduleContentEdit = screenBuilders.editor(ContentItem.class, this).withScreenClass(ContentItemEdit.class).withOpenMode(OpenMode.DIALOG).editEntity(contentItem).withAfterCloseListener(closeEvent -> {
            renderItems();
        }).build();
        moduleContentEdit.show();
    }


}