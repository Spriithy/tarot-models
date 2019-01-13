package org.tarot.models;

import java.util.prefs.Preferences;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Théophile Dano
 *
 */
public class ModelStore {

    public static final String DEFAULT_PATH = ".";
    private static String path;
    private static Preferences preferences;
    static {
        preferences = Preferences.systemNodeForPackage(ModelStore.class);
        setPath(preferences.get("path", DEFAULT_PATH));
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String newPath) {
        if (StringUtils.isBlank(newPath)) {
            setPath(DEFAULT_PATH);
            return;
        }
        path = newPath;
        preferences.put("path", newPath);
    }
}
