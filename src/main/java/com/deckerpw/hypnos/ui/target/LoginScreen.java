package com.deckerpw.hypnos.ui.target;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.ui.widget.Button;
import com.deckerpw.hypnos.ui.widget.Container;
import com.deckerpw.hypnos.ui.widget.*;
import com.deckerpw.hypnos.ui.window.PasswordPopup;
import com.deckerpw.hypnos.ui.window.Window;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class LoginScreen extends CursorTarget {

    private final Container container;
    private final LoginWindow loginWindow;
    private EditBox username;
    private EditBox password;
    private final Machine machine;
    private WindowManager windowManager;
    
    public LoginScreen(SwingWindow bridge) {
        super(bridge);
        this.machine = bridge.getMachine();
        container = new Container(null, 0, 0, 480, 270);
        container.addWidget(new Button(container, 480-29-8, 8, 29, 29, new BufferedImage[]{
                Registry.SHUTDOWN,
                Registry.SHUTDOWN
        }, machine::stop));

        windowManager = new WindowManager(container, 0, 0, 480, 270,machine);
        container.addWidget(new Button(container, 8,8, 32, 32, new BufferedImage[]{
                Registry.USER_MANAGER_APP,
                Registry.USER_MANAGER_APP
        }, () -> {
            windowManager.addWindow(new PasswordPopup(windowManager, "Enter admin password!") {
                @Override
                protected boolean checkPassword(String password) {
                    return machine.checkAdminPassword(password);
                }

                @Override
                protected void onSuccess(String password) {
                    windowManager.addWindow(machine.createUserManagerWindow(password ,windowManager, 20, 20), Registry.OPEN_WINDOW);
                }
            },Registry.ALERT_WINDOW);
            container.selectWidget(windowManager);
            //windowManager.addWindow(machine.createUserManagerWindow(windowManager, 20, 20), Registry.ALERT_WINDOW);
        } ));

        container.addWidget(windowManager);
        loginWindow = new LoginWindow(windowManager);
        windowManager.addWindow(loginWindow, null);
        container.selectWidget(windowManager);
        windowManager.selectWindow(loginWindow);

        //loginWindow.addWidget(new TextButton(loginWindow, 132 / 2 - 47 / 2, 70, 47, 21, "LOGIN", Registry.DEFAULT_BUTTON, this::login, Registry.HYPNOFONT_0N));
//        loginWindow.mousePressed(9, 62);
//        container.addWidget(loginWindow);
//        container.selectWidget(loginWindow);
//        loginWindow.selectWidget(username);
        container.update();

        PasswordPopup popup = machine.getFirstStartupPopup(windowManager);
        if (popup != null){
            windowManager.addWindow(popup, Registry.ALERT_WINDOW);
        }
    }

    private void login() {
        String name = username.getText();
        String pass = password.getText();
        User user = machine.login(name, pass);
        if (user != null) {
            bridge.switchTarget(user.createScreen(bridge),true);
        }else {
            password.setText("");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        container.mouseMoved(mouseX, mouseY);
        update();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        container.mouseDragged(mouseX, mouseY);
        update();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        container.mousePressed(mouseX, mouseY);
        update();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        container.mouseReleased(mouseX, mouseY);
        update();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        container.mouseWheelMoved(mouseX, mouseY, e.getScrollAmount());
        update();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        container.keyTyped(e);
        if (e.getKeyChar() == KeyEvent.VK_TAB)
            loginWindow.selectWidget(password);
        update();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        container.keyPressed(e);
        update();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        container.keyReleased(e);
        update();
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(Registry.WALLPAPERS[39].wall, 0, 0, 480, 270);
        container.render(graphics);
        paintCursor(graphics);
    }

    @Override
    public void update() {
        bridge.repaint();
    }


    private class LoginWindow extends Window {
        public LoginWindow(WindowManager windowManager) {
            super(windowManager, Registry.LOGIN_PANE,480 / 2 - 132 / 2, 270 / 2 - 107 / 2, 132, 107, "LOGIN");
            removeWidget(1);
            username = new EditBox(this, 9, 62, "USERNAME", Registry.HYPNOFONT_0N, EditBox.EVERYTHING);
            addWidget(username);
            password = new PasswordBox(this, 9, 85, "PASSWORD", Registry.HYPNOFONT_0N, EditBox.EVERYTHING);
            addWidget(password);
            username.setOnReturn(() -> this.selectWidget(password));
            password.setOnReturn(LoginScreen.this::login);
            selectWidget(username);
        }
    }
}
