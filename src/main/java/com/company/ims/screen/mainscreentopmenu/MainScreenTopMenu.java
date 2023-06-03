package com.company.ims.screen.mainscreentopmenu;

import com.company.ims.entity.User;
import com.company.ims.screen.user.UserEdit;
import com.company.ims.security.LecturerRole;
import com.company.ims.security.StudentRole;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.securityui.screen.changepassword.ChangePasswordDialog;
import io.jmix.ui.*;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.themesettings.ThemeSettingsScreen;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.AppMenu;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
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
    private AppMenu rightMenu;

    @Autowired
    private Image userImage;

    @Autowired
    private Notifications notifications;

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private SideMenu sideMenu;

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

        hideUnwantedScreensForAdmin(isStudent, isLecturer);

        createRightMenu();
    }

    private void hideUnwantedScreensForAdmin(boolean isStudent, boolean isLecturer) {
        // hiding unwanted menu items from admin user
        if (!isStudent && mainMenu.getMenuItem("StudentHomeScreen") != null) {
            mainMenu.removeMenuItem(Objects.requireNonNull(mainMenu.getMenuItem("StudentHomeScreen")));
        }

        if (!isLecturer && mainMenu.getMenuItem("LecturerHomeScreen") != null) {
            mainMenu.removeMenuItem(Objects.requireNonNull(mainMenu.getMenuItem("LecturerHomeScreen")));
        }

        if (!isLecturer && mainMenu.getMenuItem("CashierHomeScreen") != null) {
            mainMenu.removeMenuItem(Objects.requireNonNull(mainMenu.getMenuItem("CashierHomeScreen")));
        }

        if (!isStudent && sideMenu.getMenuItem("StudentHomeScreen") != null) {
            sideMenu.removeMenuItem(Objects.requireNonNull(sideMenu.getMenuItem("StudentHomeScreen")));
        }

        if (!isLecturer && sideMenu.getMenuItem("LecturerHomeScreen") != null) {
            sideMenu.removeMenuItem(Objects.requireNonNull(sideMenu.getMenuItem("LecturerHomeScreen")));
        }

        if (!isLecturer && sideMenu.getMenuItem("CashierHomeScreen") != null) {
            sideMenu.removeMenuItem(Objects.requireNonNull(sideMenu.getMenuItem("CashierHomeScreen")));
        }
    }

    private void createRightMenu() {
        userImage.setSource(ClasspathResource.class)
                .setPath("com/company/ims/screen/mainscreentopmenu/defaultProfileImage.png");
        List<AppMenu.MenuItem> menuItems = rightMenu.getMenuItems();
        for (AppMenu.MenuItem menuItem : menuItems) {
            rightMenu.removeMenuItem(menuItem);
        }
        AppMenu.MenuItem rootItem = rightMenu.createMenuItem("settings", "", "font-icon:LIST", null);
        AppMenu.MenuItem changeTheme = rightMenu.createMenuItem("changeTheme", "Change Theme",
                "font-icon:PAINT_BRUSH", menuItem -> screenBuilders.screen(this)
                        .withScreenClass(ThemeSettingsScreen.class)
                        .withOpenMode(OpenMode.NEW_TAB)
                        .build()
                        .show());
        AppMenu.MenuItem changePassword = rightMenu.createMenuItem("changePassword", "Change Password",
                "font-icon:LOCK", menuItem -> {
                    showChangePasswordDialog();
                });
        AppMenu.MenuItem logout = rightMenu.createMenuItem("logout", "Log out",
                "font-icon:SIGN_OUT",
                menuItem -> dialogs.createInputDialog(this)
                        .withCaption("Do you want to sign out ?")
                        .withActions(DialogActions.YES_NO)
                        .withCloseListener(inputDialogCloseEvent -> {
                            if (inputDialogCloseEvent.closedWith(DialogOutcome.YES)) {
                                App.getInstance().logout();
                            }
                        }).show()
        );
        rootItem.addChildItem(changeTheme);
        rootItem.addChildItem(changePassword);
        rootItem.addChildItem(rightMenu.createSeparator());
        rootItem.addChildItem(logout);

        rightMenu.addMenuItem(rootItem, 0);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }

    @Install(to = "userIndicator", subject = "formatter")
    private String userIndicatorFormatter(UserDetails value) {
        return ((User) value).getFullName();
    }

    @Install(to = "userIndicator2", subject = "formatter")
    private String userIndicator2Formatter(UserDetails value) {
        return userIndicatorFormatter(value);
    }

    @Subscribe("userImage")
    public void onUserImageClick(Image.ClickEvent event) {
        User user = (User) currentAuthentication.getUser();
        UserEdit userEditor = (UserEdit) screenBuilders.editor(User.class, this)
                .editEntity(user)
                .withScreenClass(UserEdit.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();
        userEditor.setOwnUserEditor(true);
        userEditor.addAfterCloseListener(afterCloseEvent -> {
            notifications.create()
                    .withCaption("Some changes will be applied after next sign in")
                    .withType(Notifications.NotificationType.HUMANIZED)
                    .show();
        });
        userEditor.show();
    }

    public void showChangePasswordDialog() {
        final Screen frameOwner = Objects.requireNonNull(AppUI.getCurrent()).getTopLevelWindowNN().getFrameOwner();

        screenBuilders.screen(frameOwner)
                .withScreenClass(ChangePasswordDialog.class)
                .build()
                .withUsername(currentAuthentication.getUser().getUsername())
                .withCurrentPasswordRequired(true)
                .show();
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }
}