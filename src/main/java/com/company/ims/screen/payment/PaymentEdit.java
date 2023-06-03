package com.company.ims.screen.payment;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Payment;

@UiController("Payment.edit")
@UiDescriptor("payment-edit.xml")
@EditedEntityContainer("paymentDc")
public class PaymentEdit extends StandardEditor<Payment> {
}