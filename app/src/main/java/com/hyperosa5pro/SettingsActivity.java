package com.hyperosa5pro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.*;

public class SettingsActivity extends Activity {
    LinearLayout root;
    int text, bg, accent;
    GradientDrawable shape(int c){GradientDrawable d=new GradientDrawable();d.setColor(c);d.setCornerRadius(28);return d;}
    TextView item(String s){TextView t=new TextView(this);t.setText(s);t.setTextSize(16);t.setTextColor(text);t.setPadding(18,17,18,17);t.setBackground(shape(ThemeStore.dark(this)?Color.rgb(35,45,62):Color.WHITE));return t;}
    @Override public void onCreate(Bundle b){super.onCreate(b);render();}
    void render(){
        boolean dark=ThemeStore.dark(this); text=dark?Color.WHITE:Color.rgb(25,35,50);bg=dark?Color.rgb(16,22,34):Color.rgb(241,246,255);accent=ThemeStore.accent(this);
        root=new LinearLayout(this);root.setOrientation(1);root.setPadding(20,28,20,20);root.setBackgroundColor(bg);setContentView(root);
        TextView title=item("Ajustes HYPEROS A5 PRO");title.setTextSize(25);root.addView(title);
        TextView mode=item(dark?"☀  Activar modo claro":"☾  Activar modo oscuro");root.addView(mode);mode.setOnClickListener(v->{ThemeStore.setMode(this,!ThemeStore.dark(this));render();});
        TextView themes=item("🎨  Abrir Temas (100 diseños)");root.addView(themes);themes.setOnClickListener(v->startActivity(new Intent(this,ThemesActivity.class)));
        TextView android=item("⚙  Ajustes de Android");root.addView(android);android.setOnClickListener(v->startActivity(new Intent(Settings.ACTION_SETTINGS)));
        TextView home=item("⌂  Volver al inicio");root.addView(home);home.setOnClickListener(v->finish());
        TextView note=item("Color de acento: se cambia al aplicar un tema.");note.setTextColor(text);note.setPadding(4,20,4,8);root.addView(note);
    }
}
