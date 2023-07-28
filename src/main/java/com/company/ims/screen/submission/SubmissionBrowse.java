package com.company.ims.screen.submission;

import com.company.ims.entity.ContentItem;
import com.company.ims.entity.Submission;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Submission.browse")
@UiDescriptor("submission-browse.xml")
@LookupComponent("submissionsTable")
public class SubmissionBrowse extends StandardLookup<Submission> {
    private ContentItem contentItem;
    @Autowired
    private CollectionLoader<Submission> submissionsDl;

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        submissionsDl.setParameter("contentItem", contentItem);
        submissionsDl.load();
    }


}