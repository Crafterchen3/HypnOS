package com.deckerpw.hypnos;

import com.deckerpw.hypnos.swing.MainFrame;
import com.deckerpw.hypnos.util.Application;

import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Optional;

public class HypnOS {

    public static float size = 1;
    public static ArrayList<Application> mods = new ArrayList<>();
    public static String VERSION = "0y31d12mA";

    public static void main(String[] args) {
        runClient();
    }

    public static void runClient() {
        new Loader().loadMods();
        Registry.STARTUP.playSound();
        setFullscreenIfPossible();
        newSize(0);
    }

    public static void runClient(Application mod) {
        mods.add(mod);
        runClient();
    }

    private static void setFullscreenIfPossible() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if ((screenSize.width / 480F) % 1 == 0 && (screenSize.height / 270F) % 1 == 0) {
            HypnOS.size = screenSize.width / 480;
        }
    }

    public static void newSize(int i) {
        size += i;
        new MainFrame();
    }

    private static class Loader {

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
            ArrayList<File> files = findMods("C:/Users/decke/Development/HypnOS/mods"); //TODO do it right
            for (File file : files) {
                try {
                    mods.add(load(file));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}