package com.company.ims.screen.itemresource;

import io.jmix.ui.screen.*;
import com.company.ims.entity.ItemResource;

@UiController("ItemResource.edit")
@UiDescriptor("item-resource-edit.xml")
@EditedEntityContainer("itemResourceDc")
public class ItemResourceEdit extends StandardEditor<ItemResource> {
}