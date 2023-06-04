package com.company.ims.screen.contentitem;

import com.company.ims.entity.ContentItem;
import com.company.ims.entity.Student;
import com.company.ims.entity.Submission;
import com.company.ims.entity.User;
import com.company.ims.screen.submission.SubmissionEdit;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.LogicalCondition;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
    private HBoxLayout resourcesBoxFiles;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;
    @Autowired
    private Button editBtn;
    @Autowired
    private VBoxLayout submissionsBox;
    @Autowired
    private Label<String> submissionText;
    @Autowired
    private LinkButton submissionFileBtn;
    @Autowired
    private Button submissionCreateBtn;
    @Autowired
    private Button submissionEditBtn;
    @Autowired
    private Button submissionRemoveBtn;
    private Submission studentSubmission;
    @Autowired
    private Label<String> marksLabel;
    @Autowired
    private Button evaluationBtn;

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
            resourcesBoxFiles.add(files);
        } else {
            resourcesBox.setVisible(false);
        }

        if (user.isStudent()) {
            submissionsBox.setVisible(true);

            Optional<Submission> submission = dataManager.load(Submission.class)
                    .condition(
                            LogicalCondition.and(
                                    PropertyCondition.equal("student", user),
                                    PropertyCondition.equal("contentItem", contentItem)
                            )
                    ).optional();
            if (submission.isPresent()) {
                submissionText.setVisible(true);
                submissionFileBtn.setVisible(true);

                studentSubmission = submission.get();
                submissionCreateBtn.setVisible(false);
                submissionEditBtn.setVisible(true);
                submissionRemoveBtn.setVisible(true);

                submissionText.setValue(submission.get().getText());
                if (studentSubmission.getFile() != null) {
                    submissionFileBtn.setCaption(studentSubmission.getFile().getFileName());
                    submissionFileBtn.addClickListener(clickEvent -> downloader.download(studentSubmission.getFile()));
                } else {
                    submissionFileBtn.setVisible(false);
                }
                if (studentSubmission.getMarks() != null) {
                    marksLabel.setVisible(true);
                    marksLabel.setValue("Marks given: " + studentSubmission.getMarks());
                }
            } else {
                submissionCreateBtn.setVisible(true);
                submissionEditBtn.setVisible(false);
                submissionRemoveBtn.setVisible(false);

                submissionText.setVisible(false);
                submissionFileBtn.setVisible(false);
            }
        }
        if (user.isLecturer()) {
            evaluationBtn.setVisible(true);
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

    @Subscribe("submissionCreateBtn")
    public void onSubmissionCreateBtnClick(Button.ClickEvent event) {
        Submission submission = dataManager.create(Submission.class);
        submission.setStudent((Student) user);
        submission.setContentItem(contentItem);
        SubmissionEdit submissionEdit = screenBuilders.editor(Submission.class, this)
                .withScreenClass(SubmissionEdit.class)
                .editEntity(submission)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(closeEvent -> {
                    this.contentItem = dataManager.load(ContentItem.class)
                            .id(contentItem.getId())
                            .fetchPlan("contentItem-fetch-plan")
                            .one();
                    this.renderComponent();
                })
                .build();
        submissionEdit.setUser(user);
        submissionEdit.show();
    }

    @Subscribe("submissionEditBtn")
    public void onSubmissionEditBtnClick(Button.ClickEvent event) {
        SubmissionEdit submissionEdit = screenBuilders.editor(Submission.class, this)
                .withScreenClass(SubmissionEdit.class)
                .editEntity(studentSubmission)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(closeEvent -> {
                    this.contentItem = dataManager.load(ContentItem.class)
                            .id(contentItem.getId())
                            .fetchPlan("contentItem-fetch-plan")
                            .one();
                    this.renderComponent();
                })
                .build();
        submissionEdit.setUser(user);
        submissionEdit.show();
    }

    @Subscribe("submissionRemoveBtn")
    public void onSubmissionRemoveBtnClick(Button.ClickEvent event) {
        dataManager.remove(studentSubmission);
        renderComponent();
    }

    @Subscribe("evaluationBtn")
    public void onEvaluationBtnClick(Button.ClickEvent event) {
        //show evaluation screen
    }
}