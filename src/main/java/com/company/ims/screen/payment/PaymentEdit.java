package com.company.ims.screen.payment;

import com.company.ims.entity.Payment;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Payment.edit")
@UiDescriptor("payment-edit.xml")
@EditedEntityContainer("paymentDc")
public class PaymentEdit extends StandardEditor<Payment> {
}