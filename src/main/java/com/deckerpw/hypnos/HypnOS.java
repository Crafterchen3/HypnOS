package com.deckerpw.hypnos;

import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.ui.Screen;
import com.deckerpw.hypnos.util.Application;
import com.deckerpw.hypnos.util.Logger;
import org.json.JSONException;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class HypnOS {

    public static float size = 1;
    public static ArrayList<Application> mods = new ArrayList<>();
    public static String Path;
    public static String VERSION = "1y03d01mA";
    public static Settings settings;
    public static Logger logger;

    public static void main(String[] args) {
        String path = "";
        if (args.length > 0)
            path = args[0];
        try {
            runClient(path);
        } catch (JSONException e) {
            logger.println("JSONException thrown, resetting Settings...");
            settings.loadDefaults();
            runClient(path);
        }
    }

    public static void exit() {
        settings.jsonObject.put("desktop", Screen.getInstance().desktop.getIconConfig());
        settings.save();
        logger.save();
        System.exit(0);
    }

    public static void throwErrorDialog(String dialog) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, dialog);
        System.exit(1);
    }

    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);

        // Check if the folder exists
        if (!folder.exists()) {
            try {
                // Create the folder if it doesn't exist
                boolean created = folder.mkdirs();
                if (created) {
                    logger.println("Folder created successfully at: " + folder.getAbsolutePath());
                } else {
                    throwErrorDialog("Failed to create config folder.");
                }
            } catch (SecurityException e) {
                throwErrorDialog("Failed to create config folder.");
            }
        }
    }

    public static void runClient(String path) {
        logger = new Logger();
        if (path != null && path.isEmpty()){
            HypnOS.Path = Paths.get(System.getProperty("user.home"), "HypnOS").toString();
            createFolder(HypnOS.Path);
        }else
            HypnOS.Path = path;
        createFolder(Paths.get(path, "logs").toString());
        settings = new Settings();
        settings.load();
        new Loader().loadMods();
        Registry.STARTUP.playSound(20);
        newSize(0);
    }

    public static void runClient(Application mod, String path) {
        mods.add(mod);
        runClient(path);
    }

    public static void newSize(int i) {
        size += i;
        new SwingWindow();
    }

    private static class Loader {

        public String[] errors = new String[]{
                "Looks like our mods took a spontaneous vacation and refused to load!",
                "Our attempt to summon the mods hit a snag—seems they're on a coffee break.",
                "The mods tried to perform a magic trick but got stuck in mid-air.",
                "We asked the mods to join the party, but they're lost somewhere in cyberspace.",
                "Our mods decided to play hide and seek. So far, they're winning.",
                "The mods went on strike and are currently staging a sit-in protest.",
                "We told the mods it's showtime, but they're still practicing their dramatic entrance.",
                "Our mods are taking the scenic route – it seems they're sightseeing instead of loading.",
                "We offered the mods some cookies to load faster, but they seem to be on a snack break.",
                "Our mods are having an identity crisis – they're unsure if they're mods or mood."
        };

        public Optional<String> getFileExtension(String filename) {
            return Optional.ofNullable(filename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(filename.lastIndexOf(".") + 1));
        }

        public ArrayList<File> findMods(String modDir) {
            ArrayList<File> results = new ArrayList<>();


            File[] files = new File(modDir).listFiles();
            //If this pathname does not denote a directory, then listFiles() returns null.

            for (File file : files) {
                if (file.isFile() && getFileExtension(file.getName()).get().equals("jar")) {
                    results.add(file);
                }
            }
            return results;
        }

        public Application load(File file) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, MalformedURLException {
            URLClassLoader child = new URLClassLoader(
                    new URL[]{file.toURI().toURL()},
                    this.getClass().getClassLoader()
            );
            Class classToLoad = Class.forName("com.deckerpw.mod.Main", true, child);//TODO make manifest
            Method method = classToLoad.getDeclaredMethod("getApplication");
            Object instance = classToLoad.newInstance();
            Application result = (Application) method.invoke(instance);
            return result;
        }

        public void loadMods() {
            try {
                String path = Paths.get(Path, "mods").toString();
                createFolder(path);
                ArrayList<File> files = findMods(path);
                for (File file : files) {
                    try {
                        mods.add(load(file));
                    } catch (Exception e) {
                        throwErrorDialog(errors[(int) (Math.random() * errors.length)]);
                    }
                }
            } catch (Exception e) {
                throwErrorDialog(errors[(int) (Math.random() * errors.length)]);
            }

        }

    }

}