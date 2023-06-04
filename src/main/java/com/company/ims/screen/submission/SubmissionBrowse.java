package com.company.ims.screen.submission;

import com.company.ims.entity.Submission;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Submission.browse")
@UiDescriptor("submission-browse.xml")
@LookupComponent("submissionsTable")
public class SubmissionBrowse extends StandardLookup<Submission> {
}