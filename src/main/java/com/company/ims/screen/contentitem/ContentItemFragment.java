package com.company.ims.screen.contentitem;

import com.company.ims.entity.ContentItem;
import com.company.ims.entity.User;
import io.jmix.core.DataManager;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.icon.JmixIcon;
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
    @Autowired
    private HBoxLayout resourcesBox;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;
    @Autowired
    private Button editBtn;

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        if (user.isLecturer() || user.isAdmin()) {
            editBtn.setVisible(true);
            editBtn.setEnabled(true);
        }
        renderComponent();
    }

    private void renderComponent() {
        title.setValue(contentItem.getTitle());
        description.setValue(contentItem.getDescription());

        if (contentItem.getResources() != null && contentItem.getResources().size() > 0) {
            resourcesBox.setVisible(true);
            LinkButton[] files = contentItem.getResources().stream()
                    .map(r -> {
                        LinkButton linkButton = uiComponents.create(LinkButton.class);
                        linkButton.setIconFromSet(JmixIcon.FILE);
                        linkButton.setAction(new BaseAction("download")
                                .withCaption(r.getFile().getFileName())
                                .withHandler(actionPerformedEvent ->
                                        downloader.download(r.getFile())
                                )
                        );
                        return linkButton;
                    })
                    .toArray(LinkButton[]::new);
            resourcesBox.add(files);
        } else {
            resourcesBox.setVisible(false);
        }
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