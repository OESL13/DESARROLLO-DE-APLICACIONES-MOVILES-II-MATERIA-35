package com.example.actividad1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ViewActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private final int[] playIds = {
            R.id.play1, R.id.play2, R.id.play3, R.id.play4, R.id.play5,
            R.id.play1, R.id.play2, R.id.play3, R.id.play4, R.id.play5
    };

    // CORREGIDO: aquí van los 10 ids reales
    private final int[] plays = {
            R.id.play1, R.id.play2, R.id.play3, R.id.play4, R.id.play5,
            R.id.play6, R.id.play7, R.id.play8, R.id.play9, R.id.play10
    };

    private final int[] shares = {
            R.id.share1, R.id.share2, R.id.share3, R.id.share4, R.id.share5,
            R.id.share6, R.id.share7, R.id.share8, R.id.share9, R.id.share10
    };

    private final int[] blues = {
            R.id.blue1, R.id.blue2, R.id.blue3, R.id.blue4, R.id.blue5,
            R.id.blue6, R.id.blue7, R.id.blue8, R.id.blue9, R.id.blue10
    };

    private final int[] audios = {
            R.raw.bart_ay_caramba,
            R.raw.homero_simpson_iujuu,
            R.raw.iphone_notificacion,
            R.raw.la_pinata_minions,
            R.raw.latigo_5,
            R.raw.mario_bros_tuberia,
            R.raw.mario_bros_vida,
            R.raw.messenger_tono_mensaje,
            R.raw.nelson_aha,
            R.raw.r2d2_9_2
    };

    private final String[] nombres = {
            "bart_ay_caramba.mp3",
            "homero_simpson_jujuu.mp3",
            "iphone_notificacion.mp3",
            "la_pinata_minions.mp3",
            "latigo_5.mp3",
            "mario_bros_tuberia.mp3",
            "mario_bros_vida.mp3",
            "messenger_tono_mensaje.mp3",
            "nelson_aha.mp3",
            "r2d2_9_2.mp3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        for (int i = 0; i < 10; i++) {
            final int index = i;

            ImageButton btnPlay = findViewById(plays[i]);
            ImageButton btnShare = findViewById(shares[i]);
            ImageButton btnBlue = findViewById(blues[i]);

            btnPlay.setOnClickListener(v -> reproducir(audios[index]));

            btnShare.setOnClickListener(v -> compartir(audios[index], nombres[index]));

            btnBlue.setOnClickListener(v -> {
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                Toast.makeText(this, "Enciende el Bluetooth y comparte el audio", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void reproducir(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(this, resId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private void compartir(int resId, String nombreArchivo) {
        try {
            InputStream input = getResources().openRawResource(resId);
            File file = new File(getCacheDir(), nombreArchivo);
            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
            output.close();
            input.close();

            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("audio/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Compartir audio"));
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo compartir", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
