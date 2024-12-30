package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.config.IConfig;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import org.json.JSONObject;

public abstract class Module {

    protected IConfig config;

    public abstract String getId();

    public abstract DesktopIcon[] getDesktopIcons();

//    public void setConfig(IConfig config) {
//        if (this.config == null) {
//            this.config = config;
//        }
//    }
}
