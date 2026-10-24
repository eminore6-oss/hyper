package com.hyperosa5pro;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import java.util.Locale;

public class ThemesActivity extends Activity {
    LinearLayout root, list;
    int text,bg;
    String[] names={"Cielo Azul","Lavanda","Menta","Rosa Suave","Atardecer","Océano","Violeta","Bosque","Coral","Grafito"};
    GradientDrawable shape(int color){GradientDrawable d=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{color,lighten(color)});d.setCornerRadius(25);return d;}
    int lighten(int c){return Color.rgb(Math.min(255,Color.red(c)+35),Math.min(255,Color.green(c)+35),Math.min(255,Color.blue(c)+35));}
    TextView label(String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(text);return t;}
    @Override public void onCreate(Bundle b){super.onCreate(b);render();}
    void render(){
        boolean dark=ThemeStore.dark(this);text=dark?Color.WHITE:Color.rgb(25,35,50);bg=dark?Color.rgb(16,22,34):Color.rgb(241,246,255);
        root=new LinearLayout(this);root.setOrientation(1);root.setPadding(18,24,18,12);root.setBackgroundColor(bg);setContentView(root);
        root.addView(label("Temas",28));root.addView(label("100 estilos para personalizar HYPEROS A5 PRO",14));
        ScrollView sc=new ScrollView(this);list=new LinearLayout(this);list.setOrientation(1);sc.addView(list);root.addView(sc,new LinearLayout.LayoutParams(-1,0,1));
        for(int i=0;i<100;i+=2){
            LinearLayout row=new LinearLayout(this);row.setOrientation(0);
            for(int j=i;j<Math.min(i+2,100);j++){
                final int idx=j;int[] colors={Color.rgb(0,122,255),Color.rgb(115,85,220),Color.rgb(0,170,145),Color.rgb(235,90,125),Color.rgb(245,145,45),Color.rgb(40,110,200),Color.rgb(145,75,210),Color.rgb(30,145,90),Color.rgb(220,75,65),Color.rgb(65,90,120)};
                LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setPadding(10,10,10,10);card.setBackground(shape(colors[j%10]));
                TextView title=new TextView(this);title.setText(String.format(Locale.getDefault(),"Tema %02d",j+1));title.setTextColor(Color.WHITE);title.setTextSize(16);title.setGravity(Gravity.CENTER);
                TextView desc=new TextView(this);desc.setText(names[j%names.length]);desc.setTextColor(Color.WHITE);desc.setGravity(Gravity.CENTER);
                Button apply=new Button(this);apply.setText("Aplicar");apply.setOnClickListener(v->{ThemeStore.applyTheme(this,idx);Toast.makeText(this,"Tema "+(idx+1)+" aplicado",Toast.LENGTH_SHORT).show();});
                card.addView(title);card.addView(desc);card.addView(apply);
                LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(0,-2,1);cp.setMargins(5,8,5,8);row.addView(card,cp);
            }
            list.addView(row);
        }
        Button back=new Button(this);back.setText("Volver");back.setOnClickListener(v->finish());root.addView(back);
    }
}
