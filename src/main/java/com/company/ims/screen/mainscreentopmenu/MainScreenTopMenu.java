package com.company.ims.screen.mainscreentopmenu;

import com.company.ims.security.LecturerRole;
import com.company.ims.security.StudentRole;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.AppMenu;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@UiController("MainTop")
@UiDescriptor("main-screen-top-menu.xml")
@Route(path = "main", root = true)
public class MainScreenTopMenu extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private AppMenu mainMenu;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        boolean isStudent = false;
        boolean isLecturer = false;

        Collection<? extends GrantedAuthority> authorities = currentAuthentication.getAuthentication().getAuthorities();

        Optional<String> studentRoleFound = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.equals(StudentRole.CODE))
                .findAny();
        isStudent = studentRoleFound.isPresent();

        Optional<String> lecturerRoleFound = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.equals(LecturerRole.CODE))
                .findAny();
        isLecturer = lecturerRoleFound.isPresent();

        // hiding unwanted menu items from admin
        if (!isStudent && mainMenu.getMenuItem("StudentHomeScreen") != null) {
            mainMenu.removeMenuItem(Objects.requireNonNull(mainMenu.getMenuItem("StudentHomeScreen")));
        }

        if (!isLecturer && mainMenu.getMenuItem("LecturerHomeScreen") != null) {
            mainMenu.removeMenuItem(Objects.requireNonNull(mainMenu.getMenuItem("LecturerHomeScreen")));
        }

        if (!isLecturer && mainMenu.getMenuItem("CashierHomeScreen") != null) {
            mainMenu.removeMenuItem(Objects.requireNonNull(mainMenu.getMenuItem("CashierHomeScreen")));
        }

    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }
}