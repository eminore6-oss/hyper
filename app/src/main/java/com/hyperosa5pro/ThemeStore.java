package com.hyperosa5pro;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public final class ThemeStore {
    private static final String PREFS = "hyperos_a5_prefs";
    private ThemeStore() {}
    static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
    static boolean dark(Context c) {
        return prefs(c).getBoolean("dark", false);
    }
    static int accent(Context c) {
        return prefs(c).getInt("accent", Color.rgb(0, 122, 255));
    }
    static int background(Context c) {
        return prefs(c).getInt("background", Color.rgb(241, 246, 255));
    }
    static void setMode(Context c, boolean dark) {
        prefs(c).edit().putBoolean("dark", dark).apply();
        if (dark) prefs(c).edit().putInt("background", Color.rgb(16, 22, 34)).apply();
        else prefs(c).edit().putInt("background", Color.rgb(241, 246, 255)).apply();
    }
    static void applyTheme(Context c, int index) {
        int[] accents = {
            Color.rgb(0,122,255), Color.rgb(92,70,220), Color.rgb(0,170,150),
            Color.rgb(238,85,120), Color.rgb(245,140,35), Color.rgb(40,110,200),
            Color.rgb(135,75,210), Color.rgb(20,150,100), Color.rgb(220,70,65),
            Color.rgb(60,90,120)
        };
        int[] backgrounds = {
            Color.rgb(241,246,255), Color.rgb(247,240,255), Color.rgb(235,250,246),
            Color.rgb(255,240,245), Color.rgb(255,247,235), Color.rgb(232,242,255),
            Color.rgb(245,237,255), Color.rgb(235,248,239), Color.rgb(255,239,237),
            Color.rgb(236,242,247)
        };
        int slot = index % accents.length;
        prefs(c).edit().putInt("theme_index", index)
            .putInt("accent", accents[slot]).putInt("background", backgrounds[slot])
            .putBoolean("dark", false).apply();
    }
}
