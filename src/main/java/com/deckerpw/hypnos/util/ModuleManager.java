package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.config.FileConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class ModuleManager {

    private static ArrayList<Module> modules = new ArrayList<>();
    private ClassLoader lastLoader = this.getClass().getClassLoader();


    public static Module getModule(String id){
        for (Module module : modules) {
            if (module.getId().equals(id)) {
                return module;
            }
        }
        return null;
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static void addModule(Module module) {
        modules.add(module);
    }

    public Module loadFile(File file) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException, NoSuchMethodException {
        System.out.println("Loading " + file.getPath());
        lastLoader = new URLClassLoader(
                new URL[]{file.toURI().toURL()},
                lastLoader
        );
        JarInputStream jarStream = new JarInputStream(new FileInputStream(file));
        Manifest mf = jarStream.getManifest();
        Class classToLoad = Class.forName(mf.getMainAttributes().getValue("HypnOS-Class"), true, lastLoader);
        Module instance = (Module) classToLoad.getDeclaredConstructor().newInstance();
//        FileConfig config = new FileConfig(Paths.get(file.getParent(), file.getName() + ".json").toString()) {
//            @Override
//            public void loadDefaults() {
//
//            }
//        };
        ModuleManager.modules.add(instance);
        return instance;
    }

    public ArrayList<Module> loadDirectory(String path) {
        File folder = new File(path);
        ArrayList<Module> modules = new ArrayList<>();
        File[] files = folder.listFiles();
        for (File file : files) {
            try {
                loadFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ModuleManager.modules.addAll(modules);
        return modules;
    }


}
