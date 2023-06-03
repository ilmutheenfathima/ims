package com.company.ims.screen.enrolment;

import com.company.ims.entity.Classroom;
import com.company.ims.entity.Enrolment;
import com.company.ims.entity.Payment;
import com.company.ims.screen.payment.PaymentBrowse;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;

@UiController("Enrolment.browse")
@UiDescriptor("enrolment-browse.xml")
@LookupComponent("enrolmentsTable")
public class EnrolmentBrowse extends StandardLookup<Enrolment> {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private Classroom classroom;
    @Autowired
    private CollectionLoader<Enrolment> enrolmentsDl;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Label<String> moduleFeeLabel;
    @Autowired
    private Label<String> modulePaymentOptionLabel;

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Enrolments of " + classroom.getIdentifiableName());
        moduleFeeLabel.setValue("Course fee: Rs " + DECIMAL_FORMAT.format(classroom.getIntakeModule().getModuleContent().getModuleFee()));
        modulePaymentOptionLabel.setValue("Payment Options: " + classroom.getIntakeModule().getModuleContent().getPaymentOptions());
        enrolmentsDl.setParameter("classroom", classroom);
        enrolmentsDl.load();
    }

    @Install(to = "enrolmentsTable.totalPaid", subject = "columnGenerator")
    private Component enrolmentsTableTotalPaidColumnGenerator(Enrolment enrolment) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption("Rs " + DECIMAL_FORMAT.format(getTotalPaid(enrolment)));
        linkButton.setId(classroom.getId().toString());
        linkButton.setStyleName("huge");
        linkButton.setAlignment(Component.Alignment.MIDDLE_LEFT);
        linkButton.addClickListener(clickEvent -> {
            PaymentBrowse paymentBrowse = screenBuilders.screen(this)
                    .withScreenClass(PaymentBrowse.class)
                    .withOpenMode(OpenMode.THIS_TAB)
                    .build();
            paymentBrowse.setEnrolment(enrolment);
            paymentBrowse.show();
        });
        return linkButton;
    }

    private Double getTotalPaid(Enrolment enrolment) {
        if (enrolment.getPayments() != null) {
            return enrolment.getPayments().stream()
                    .map(Payment::getAmount)
                    .reduce(0.0, Double::sum);
        }
        return 0.0;
    }
}