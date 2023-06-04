package com.company.ims.screen.cashier;

import com.company.ims.entity.Cashier;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Cashier.browse")
@UiDescriptor("cashier-browse.xml")
@LookupComponent("cashiersTable")
public class CashierBrowse extends StandardLookup<Cashier> {
}