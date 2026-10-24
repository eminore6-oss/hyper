package com.hyperosa5pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private LinearLayout root, appGrid;
    private EditText search;
    private boolean drawer = false;
    private float downY;
    private int textColor, mutedColor, accent, bg;
    private final List<ApplicationInfo> apps = new ArrayList<>();
    private final List<ApplicationInfo> shown = new ArrayList<>();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        refreshColors();
        renderHome();
    }

    private void refreshColors() {
        accent = ThemeStore.accent(this);
        bg = ThemeStore.dark(this) ? Color.rgb(16,22,34) : ThemeStore.background(this);
        textColor = ThemeStore.dark(this) ? Color.WHITE : Color.rgb(24,32,48);
        mutedColor = ThemeStore.dark(this) ? Color.rgb(175,188,208) : Color.rgb(95,110,130);
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(radius); return d;
    }
    private TextView label(String s, int size, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size);
        t.setTextColor(textColor); if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }
    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l;
    }
    private LinearLayout row() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER_VERTICAL); return l;
    }
    private void pad(View v, int p) { v.setPadding(p,p,p,p); }
    private TextView button(String text, Runnable action) {
        TextView b = label(text, 15, true); b.setGravity(Gravity.CENTER);
        b.setPadding(16,13,16,13); b.setBackground(shape(ThemeStore.dark(this)?Color.rgb(38,49,68):Color.WHITE, 28));
        b.setOnClickListener(v -> action.run()); return b;
    }
    private void base(String title) {
        root = column(); root.setPadding(20,24,20,16); root.setBackgroundColor(bg);
        setContentView(root);
        TextView top = label(title, 27, true);
        root.addView(top);
    }
    private void renderHome() {
        drawer = false; refreshColors(); base("HYPEROS A5 PRO");
        root.addView(label("Tu Redmi A5, a tu estilo", 14, false));
        TextView clock = label(new java.text.SimpleDateFormat("HH:mm", Locale.getDefault()).format(new java.util.Date()), 48, true);
        clock.setPadding(0,26,0,0); root.addView(clock);
        root.addView(label(new java.text.SimpleDateFormat("EEEE, d MMMM", new Locale("es","CO")).format(new java.util.Date()), 15, false));
        TextView weather = label("✦  Personaliza tu experiencia", 16, true);
        weather.setPadding(18,24,18,24); weather.setBackground(shape(ThemeStore.dark(this)?Color.rgb(31,48,75):Color.rgb(218,233,255), 28));
        weather.setTextColor(ThemeStore.dark(this)?Color.WHITE:Color.rgb(25,70,130));
        LinearLayout.LayoutParams wp = new LinearLayout.LayoutParams(-1,-2); wp.topMargin=24; root.addView(weather,wp);
        TextView hint = label("Desliza hacia arriba para ver tus aplicaciones", 13, false);
        hint.setGravity(Gravity.CENTER); hint.setPadding(0,22,0,16); root.addView(hint);
        LinearLayout shortcuts = row();
        shortcuts.setGravity(Gravity.CENTER);
        shortcuts.addView(button("▦  Aplicaciones", this::renderDrawer));
        shortcuts.addView(button("  Temas  ", () -> startActivity(new Intent(this, ThemesActivity.class))));
        shortcuts.addView(button("  Ajustes  ", () -> startActivity(new Intent(this, SettingsActivity.class))));
        root.addView(shortcuts);
        TextView appsButton = button("Abrir cajón de aplicaciones  ↑", this::renderDrawer);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1,-2); bp.topMargin=18; root.addView(appsButton,bp);
        root.setOnTouchListener((v,e)->{
            if(e.getAction()==MotionEvent.ACTION_DOWN){downY=e.getY(); return true;}
            if(e.getAction()==MotionEvent.ACTION_UP && downY-e.getY()>70){renderDrawer();return true;}
            return true;
        });
    }

    private void loadApps() {
        apps.clear();
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null); intent.addCategory(Intent.CATEGORY_LAUNCHER);
        for (android.content.pm.ResolveInfo ri : pm.queryIntentActivities(intent, 0)) {
            ApplicationInfo ai;
            try { ai=pm.getApplicationInfo(ri.activityInfo.packageName,0); }
            catch(Exception ex){continue;}
            if (!ai.packageName.equals(getPackageName())) apps.add(ai);
        }
        Collections.sort(apps, (a,b)->pm.getApplicationLabel(a).toString().compareToIgnoreCase(pm.getApplicationLabel(b).toString()));
    }
    private void renderDrawer() {
        drawer=true; refreshColors(); base("Aplicaciones");
        search = new EditText(this); search.setSingleLine(true); search.setTextColor(textColor);
        search.setHintTextColor(mutedColor); search.setHint("Buscar aplicaciones");
        search.setPadding(18,8,18,8); search.setBackground(shape(ThemeStore.dark(this)?Color.rgb(35,45,62):Color.WHITE,30));
        root.addView(search);
        ScrollView scroll = new ScrollView(this); appGrid=column(); scroll.addView(appGrid);
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,0,1); sp.topMargin=12; root.addView(scroll,sp);
        loadApps(); filterApps("");
        search.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(CharSequence s,int st,int c,int a){}
            public void onTextChanged(CharSequence s,int st,int before,int count){filterApps(s.toString());}
            public void afterTextChanged(android.text.Editable e){}
        });
        root.addView(button("← Inicio", this::renderHome));
    }
    private void filterApps(String q) {
        appGrid.removeAllViews();
        PackageManager pm=getPackageManager();
        List<ApplicationInfo> filtered=new ArrayList<>();
        for(ApplicationInfo ai:apps) {
            String name=pm.getApplicationLabel(ai).toString();
            if(name.toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT))) filtered.add(ai);
        }
        if(filtered.isEmpty()) { appGrid.addView(label("No se encontraron aplicaciones",15,false)); return; }
        int columns=3;
        for(int i=0;i<filtered.size();i+=columns) {
            LinearLayout r=row(); r.setGravity(Gravity.TOP);
            for(int j=i;j<Math.min(i+columns,filtered.size());j++) {
                ApplicationInfo ai=filtered.get(j);
                LinearLayout item=column(); item.setGravity(Gravity.CENTER); item.setPadding(4,12,4,12);
                ImageView icon=new ImageView(this);
                try{icon.setImageDrawable(pm.getApplicationIcon(ai));}catch(Exception ignored){}
                item.addView(icon,new LinearLayout.LayoutParams(52,52));
                TextView name=label(pm.getApplicationLabel(ai).toString(),12,false);
                name.setGravity(Gravity.CENTER); name.setMaxLines(2);
                item.addView(name);
                item.setOnClickListener(v->{
                    Intent launch=pm.getLaunchIntentForPackage(ai.packageName);
                    if(launch!=null) startActivity(launch);
                });
                r.addView(item,new LinearLayout.LayoutParams(0,-2,1));
            }
            appGrid.addView(r,new LinearLayout.LayoutParams(-1,-2));
        }
    }
    @Override public void onBackPressed() {
        if(drawer) renderHome(); else super.onBackPressed();
    }
}
