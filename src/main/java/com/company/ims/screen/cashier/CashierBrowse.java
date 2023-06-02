package com.company.ims.screen.cashier;

import io.jmix.ui.screen.*;
import com.company.ims.entity.Cashier;

@UiController("Cashier.browse")
@UiDescriptor("cashier-browse.xml")
@LookupComponent("cashiersTable")
public class CashierBrowse extends StandardLookup<Cashier> {
}