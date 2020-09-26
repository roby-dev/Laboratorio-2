package com.example.laboratorio2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView txtResultado = findViewById(R.id.txtResultado);
        String nombres = getIntent().getStringExtra("nombres");
        String apellidos = getIntent().getStringExtra("apellidos");
        String celular = getIntent().getStringExtra("celular");
        String documento = getIntent().getStringExtra("documento");
        String direccion = getIntent().getStringExtra("direccion");

        Intent intent = getIntent();

        Bitmap foto = (Bitmap) intent.getParcelableExtra("foto");
        String resultado = nombres + " " + apellidos + "\n" + celular + "\n" + documento + "\n" + direccion;

        ImageView img = findViewById(R.id.ivResultImg);
        img.setImageBitmap(foto);
        txtResultado.setText(resultado);


        findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}