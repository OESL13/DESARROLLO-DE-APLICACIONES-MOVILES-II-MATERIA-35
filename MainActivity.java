package com.example.actividad1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);

        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arribaabajo);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajoarriba);

        ImageView logoimageview = findViewById(R.id.imageview);
        TextView audiotextview = findViewById(R.id.audiotextview);

        audiotextview.setAnimation(animacion1);
        logoimageview.setAnimation(animacion2);

        // ESTE ES EL PUENTE A LA PANTALLA 2
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
                finish();
            }
        }, 6000);
    }
}
