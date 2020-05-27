package com.example.aplicacioncamara;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;
@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button buttonAbrirCamara;
    String encoded;
    byte[] byteArray;
    Bitmap bitmap;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageviewCamara);
        buttonAbrirCamara = findViewById(R.id.buttonCamara);
        imageView.setImageResource(R.drawable.ic_image_black_24dp);
        editText = findViewById(R.id.editText);

        // Comprueba el permiso de la cámara en el dispositivo.
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            },  100 );
        }
        buttonAbrirCamara.setOnClickListener(v -> {
            Intent abrirCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(abrirCamara, 100);
        });

    }

    // Al sacar una foto la visualiza.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            assert data != null;
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageView.setImageBitmap(bitmap);
        }

    }

    public void codificarImagen(View view) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream .toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        editText.setText(encoded);
    }


    public void decodificarImagen(View view) {
        byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        editText.setText(decodedByte.toString());
    }
}
