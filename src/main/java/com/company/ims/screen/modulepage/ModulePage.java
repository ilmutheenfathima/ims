package com.company.ims.screen.modulepage;

import com.company.ims.entity.IntakeModule;
import com.company.ims.entity.User;
import com.company.ims.screen.modulecontent.ModuleContent;
import io.jmix.ui.Fragments;
import io.jmix.ui.component.ScrollBoxLayout;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ModulePage")
@UiDescriptor("module-page.xml")
public class ModulePage extends Screen {
    private IntakeModule intakeModule;
    private User user;
    @Autowired
    private ScrollBoxLayout cardContainer;
    @Autowired
    private Fragments fragments;

    public void setIntakeModule(IntakeModule intakeModule) {
        this.intakeModule = intakeModule;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        renderPage();
    }

    private void renderPage() {
        cardContainer.removeAll();

        ModuleContent moduleContent = fragments.create(this, ModuleContent.class);
        moduleContent.setIntakeModule(intakeModule);
        moduleContent.setUser(user);

        cardContainer.add(moduleContent.getFragment());

    }


}