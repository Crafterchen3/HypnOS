package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.ui.widget.*;

import java.awt.*;

public class UserManagerWindow extends TabWindow {

    private SimpleSelectionList userSelectionList;
    private EditBox editUsernameBox;
    private PasswordBox editPasswordBox;
    private final EditBox createUsernameBox;
    private final PasswordBox createPasswordBox;
    private final PasswordBox adminPasswordBox;
    private User[] users;

    /**
     * Constructor for the UserManagerWindow class.
     *
     * @param windowManager The window manager where the window will be displayed
     * @param userManager   The user manager instance
     * @param x             The x-coordinate of the window
     * @param y             The y-coordinate of the window
     */
    public UserManagerWindow(WindowManager windowManager, Machine.UserManager userManager, int x, int y) {
        super(windowManager, Registry.WINDOW_PANE, x, y, 306, 164, "User Manager", Registry.USER_MANAGER_ICON, new String[]{"USERS", "CREATE", "ADMIN"});

        // Get the list of users
        users = userManager.getUsers();

        // Create an array of usernames for the selection list
        String[] usernames = new String[users.length];
        for (int i = 0; i < users.length; i++) {
            usernames[i] = users[i].getName();
        }

        // Add a box for visual separation
        addWidgetSelectedTab(new Box(container, 11, 25, 114, 132, new Color(0x13061F)), 0);

        // Create a selection list for users
        userSelectionList = new SimpleSelectionList(container, 12, 26, 112, 130, usernames);
        userSelectionList.select(0);
        addWidgetSelectedTab(userSelectionList, 0);

        // Create an edit box for usernames
        editUsernameBox = new EditBox(container, 131, 25, "USERNAME", font, EditBox.EVERYTHING);
        if (users.length > 0)
            editUsernameBox.setText(users[0].getName());
        addWidgetSelectedTab(editUsernameBox, 0);

        // Add a 'SET' button for updating username
        addWidgetSelectedTab(new TextButton(container, 248, 22, 47, 21, "SET", Registry.DEFAULT_BUTTON, () -> {
            if (users.length > 0) {
                userManager.updateUserName(users[userSelectionList.getSelectedIndex()], editUsernameBox.getText());
            }
        }, font), 0);

        // Create a password box for passwords
        editPasswordBox = new PasswordBox(container, 131, 50, "PASSWORD", font, EditBox.EVERYTHING);
        addWidgetSelectedTab(editPasswordBox, 0);

        // Add a 'SET' button for updating password
        addWidgetSelectedTab(new TextButton(container, 248, 47, 47, 21, "SET", Registry.DEFAULT_BUTTON, () -> {
            if (users.length > 0) {
                userManager.updateUserPassword(users[userSelectionList.getSelectedIndex()], editPasswordBox.getText());
                editPasswordBox.setText("");
            }
        }, font), 0);

        // Event listener for updating text fields when selection changes
        userSelectionList.addEventListener(() -> {
            editUsernameBox.setText(users[userSelectionList.getSelectedIndex()].getName());
            editPasswordBox.setText("");
        });

        // Add a 'DELETE' button for deleting users
        addWidgetSelectedTab(new TextButton(container, 131, 136, 47, 21, "DELETE", Registry.DEFAULT_BUTTON, () -> {
            if (users.length > 0) {
                userManager.deleteUser(users[userSelectionList.getSelectedIndex()]);
                updateEntries(userManager);
            }
        }, font), 0);
        // Create an edit box for creating usernames
        createUsernameBox = new EditBox(container, 11, 25, "USERNAME", font, EditBox.EVERYTHING);
        addWidgetSelectedTab(createUsernameBox, 1);

        // Create a password box for creating passwords
        createPasswordBox = new PasswordBox(container, 11, 50, "PASSWORD", font, EditBox.EVERYTHING);
        addWidgetSelectedTab(createPasswordBox, 1);

        // Add a 'CREATE' button for creating new users
        addWidgetSelectedTab(new TextButton(container, 11, 136, 47, 21, "CREATE", Registry.DEFAULT_BUTTON, () -> {
            userManager.createUser(createUsernameBox.getText(), createPasswordBox.getText());
            createUsernameBox.setText("");
            createPasswordBox.setText("");
            updateEntries(userManager);
        }, font), 1);

        // Create an edit box for creating usernames
        adminPasswordBox = new PasswordBox(container, 11, 25, "ADMIN PASSWORD", font, EditBox.EVERYTHING);
        addWidgetSelectedTab(adminPasswordBox, 2);
        addWidgetSelectedTab(new TextButton(container, 128, 22, 47, 21, "SET", Registry.DEFAULT_BUTTON, () -> {
            userManager.setAdminPassword(adminPasswordBox.getText());
            adminPasswordBox.setText("");
        }, font), 2);
    }

    private void updateEntries(Machine.UserManager userManager){
        // Get the list of users
        users = userManager.getUsers();

        // Create an array of usernames for the selection list
        String[] usernames = new String[users.length];
        for (int i = 0; i < users.length; i++) {
            usernames[i] = users[i].getName();
        }
        userSelectionList.setEntrys(usernames);
    }
}
