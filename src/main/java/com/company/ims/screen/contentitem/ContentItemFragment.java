package com.company.ims.screen.contentitem;

import com.company.ims.entity.ContentItem;
import com.company.ims.entity.User;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ContentItemFragment")
@UiDescriptor("content-item-fragment.xml")
public class ContentItemFragment extends ScreenFragment {
    @Autowired
    private Label<String> description;
    private ContentItem contentItem;
    private User user;
    @Autowired
    private Label<String> title;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataManager dataManager;

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        renderComponent();
    }

    private void renderComponent() {
        title.setValue(contentItem.getTitle());
        description.setValue(contentItem.getDescription());
    }

    @Subscribe("editBtn")
    public void onEditBtnClick(Button.ClickEvent event) {
        ContentItemEdit moduleContentEdit = screenBuilders.editor(ContentItem.class, this)
                .withScreenClass(ContentItemEdit.class)
                .withOpenMode(OpenMode.DIALOG).editEntity(contentItem).withAfterCloseListener(closeEvent -> {
                    contentItem = dataManager.load(ContentItem.class).id(contentItem.getId())
                            .fetchPlan("contentItem-fetch-plan-for-items-page")
                            .one();
                    renderComponent();
                }).build();
        moduleContentEdit.show();
    }

    public void setUser(User user) {
        this.user = user;
    }
}