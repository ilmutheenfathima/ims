package com.company.ims.screen.payment;

import com.company.ims.entity.Enrolment;
import com.company.ims.entity.Payment;
import io.jmix.core.DataManager;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;

@UiController("Payment.browse")
@UiDescriptor("payment-browse.xml")
@LookupComponent("paymentsTable")
public class PaymentBrowse extends StandardLookup<Payment> {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private Enrolment enrolment;
    @Autowired
    private CollectionLoader<Payment> paymentsDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Label<String> moduleFeeLabel;
    @Autowired
    private Label<String> modulePaymentOptionLabel;

    public void setEnrolment(Enrolment enrolment) {
        this.enrolment = enrolment;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        // getWindow().setCaption("Payments");
        moduleFeeLabel.setValue("Course fee: Rs " + DECIMAL_FORMAT.format(enrolment.getIntakeModule().getModuleContent().getModuleFee()));
        modulePaymentOptionLabel.setValue("Payment Options: " + enrolment.getIntakeModule().getModuleContent().getPaymentOptions());
        paymentsDl.setParameter("enrolment", enrolment);
        paymentsDl.load();
    }

    @Install(to = "paymentsTable.create", subject = "newEntitySupplier")
    private Payment paymentsTableCreateNewEntitySupplier() {
        Payment payment = dataManager.create(Payment.class);
        payment.setEnrolment(enrolment);
        return payment;
    }


}