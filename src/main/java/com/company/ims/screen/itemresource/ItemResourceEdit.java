package com.company.ims.screen.itemresource;

import com.company.ims.entity.ItemResource;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("ItemResource.edit")
@UiDescriptor("item-resource-edit.xml")
@EditedEntityContainer("itemResourceDc")
public class ItemResourceEdit extends StandardEditor<ItemResource> {
}