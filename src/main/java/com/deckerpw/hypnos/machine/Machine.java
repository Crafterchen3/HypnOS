package com.deckerpw.hypnos.machine;

import com.deckerpw.hypnos.config.MachineConfig;
import com.deckerpw.hypnos.machine.filesystem.Filesystem;
import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.ui.window.PasswordPopup;
import com.deckerpw.hypnos.ui.window.UserManagerWindow;
import com.deckerpw.hypnos.util.Logger;
import com.deckerpw.hypnos.util.Module;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Machine {

    private static final String[] FOLDERS = {
            "",
            "users",
            "data",
            "logs"
    };
    private boolean running;
    private int adminPasswordHash;
    private String path;
    private User currentUser;
    private ArrayList<User> users;
    private MachineConfig config;
    private Filesystem filesystem;
    private Logger logger;
    private SwingWindow window;
    private final ArrayList<Module> modules;

    public Machine(String path) {
        this.path = path;
        this.logger = new Logger(this);
        createFolders();
        this.config = new MachineConfig(this);
        config.load();
        adminPasswordHash = config.getAdminPasswordHash();
        this.filesystem = new Filesystem(this);
        this.users = config.getUsers();
        this.modules = config.getEnabledModules();
    }

    public Module[] getModules() {
        return modules.toArray(new Module[0]);
    }

    public PasswordPopup getFirstStartupPopup(WindowManager windowManager) {
        if (adminPasswordHash == 0) {
            return new PasswordPopup(windowManager, "Create admin password!") {
                @Override
                protected boolean checkPassword(String password) {
                    return true;
                }

                @Override
                protected void onSuccess(String password) {
                    adminPasswordHash = password.hashCode();
                    saveConfig();
                }
            };
        }
        return null;
    }

    private void createFolders() {
        for (String folder : FOLDERS) {
            createFolder(Paths.get(path, folder).toString());
        }
    }

    private void createFolder(String folderPath) {
        File folder = new File(folderPath);

        // Check if the folder exists
        if (!folder.exists()) {
            try {
                // Create the folder if it doesn't exist
                boolean created = folder.mkdirs();
                if (created) {
                    logger.println("Folder created successfully at: " + folder.getAbsolutePath());
                } else {
                    logger.println("Failed to create folder at: " + folder.getAbsolutePath());
                }
            } catch (SecurityException e) {
                logger.println("Failed to create folder at: " + folder.getAbsolutePath());
            }
        }
    }

    public UserManagerWindow createUserManagerWindow(String password, WindowManager windowManager, int x, int y) {
        if (!checkAdminPassword(password))
            return null;
        return new UserManagerWindow(windowManager, new UserManager(this), x, y);
    }


    public User login(String username, String password) {
        for (User user : users) {
            if (user.checkCredentials(username, password.hashCode())) {
                logger.println("User " + user.getName() + " logged in.");
                currentUser = user;
                currentUser.getConfig().updateVolume();
                return user;
            }
        }
        return null;
    }

    public void runClient() {
        if (running)
            return;
        running = true;
        window = new SwingWindow(this);
    }

    private void saveConfig() {
        config.put("admin_password_hash", adminPasswordHash);
        JSONArray users = new JSONArray();
        for (User user : this.users) {
            JSONObject userJson = new JSONObject();
            userJson.put("path", user.getPath());
            userJson.put("name", user.getName());
            userJson.put("password_hash", user.getPasswordHash());
            users.put(userJson);
        }
        JSONArray enabledModules = new JSONArray();
        for (Module module : modules) {
            enabledModules.put(module.getId());
        }
        config.put("enabled_modules", enabledModules);
        config.put("users", users);
        config.save();
    }

    public void enableModule(Module module) {
        this.modules.add(module);
        currentUser.getScreen().getDesktop().updateIcons();
        saveConfig();
    }

    public boolean checkAdminPassword(String password) {
        return adminPasswordHash == password.hashCode();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getConfigPath() {
        return Paths.get(path, "config.json").toString();
    }

    public boolean isRunning() {
        return running;
    }

    public MachineConfig getConfig() {
        return config;
    }

    public Filesystem getFilesystem() {
        return filesystem;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getPath() {
        return path;
    }

    public void stop() {
        logger.println("Stopping HypnOS...");
        logger.save();
        window.dispose();
        running = false;
    }

    public void setUserName(String name) {
        currentUser.setName(name);
        saveConfig();
    }

    public void setPassword(String passwordBoxText) {
        currentUser.setPasswordHash(passwordBoxText.hashCode());
        saveConfig();
    }

    public void disableModule(Module module) {
        modules.remove(module);
        saveConfig();
    }

    public static class UserManager {

        private final Machine machine;

        private UserManager(Machine machine) {
            this.machine = machine;
        }

        public User[] getUsers() {
            return machine.users.toArray(new User[0]);
        }

        public void createUser(String name, String password) {
            String path = name;
            String fullPath = Paths.get(machine.path, "users", path).toString();
            int i = 0;
            while (new File(fullPath).exists()) {
                path = name + "_" + i;
                fullPath = Paths.get(machine.path, "users", path).toString();
                i++;
            }
            machine.createFolder(fullPath);
            machine.createFolder(fullPath + "/data");
            machine.users.add(new User(machine, path, name, password.hashCode()));
            machine.saveConfig();
        }

        public void updateUserName(User user, String name) {
            user.setName(name);
            machine.saveConfig();
        }

        public void updateUserPassword(User user, String password) {
            user.setPasswordHash(password.hashCode());
            machine.saveConfig();
        }

        public void deleteUser(User user) {
            machine.users.remove(user);
            deleteDir(new File(machine.path + "/users/" + user.getPath()));
            machine.saveConfig();
        }

        public void setAdminPassword(String password) {
            machine.adminPasswordHash = password.hashCode();
            machine.saveConfig();
        }

        void deleteDir(File file) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    if (!Files.isSymbolicLink(f.toPath())) {
                        deleteDir(f);
                    }
                }
            }
            file.delete();
        }


    }
}
