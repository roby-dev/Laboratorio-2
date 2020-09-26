package com.example.laboratorio2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity  {

    public static String latitudGPS,lontitudGPS,direccion;
    public static Bitmap bitmap;
    public static final int CAMERA_PIC_REQUEST=1;
    private EditText txtNombres;
    private EditText txtApellidos;
    private EditText txtCelular;
    private EditText txtDocumento;
    private EditText txtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnDireccion).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FragmentMap.class);
                startActivity(intent);
            }
        });

        findViewById((R.id.btnCamara)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });

        final Button btnGuardar=findViewById(R.id.btnGuardar);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtDireccion.setEnabled(false);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarDatos()){
                    Toast.makeText(MainActivity.this, "Llene todos los datos", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("nombres",txtNombres.getText().toString());
                    intent.putExtra("apellidos",txtApellidos.getText().toString());
                    intent.putExtra("celular",txtCelular.getText().toString());
                    intent.putExtra("documento",txtDocumento.getText().toString());
                    intent.putExtra("direccion",direccion);
                    intent.putExtra("foto",bitmap);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validarDatos(){
        txtNombres = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellido);
        txtCelular = findViewById(R.id.txtCelular);
        txtDocumento = findViewById(R.id.txtDocumento);
        txtDireccion = findViewById(R.id.txtDireccion);
        ImageView ivfoto= (ImageView) findViewById(R.id.ivFoto);
       return (txtNombres.getText().toString().trim().equals("")
                ||txtApellidos.getText().toString().trim().equals("")
                ||txtCelular.getText().toString().trim().equals("")
                ||txtDocumento.getText().toString().equals("")
                ||txtDireccion.getText().toString().trim().equals("")||ivfoto.getDrawable()==null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDireccion.setText(direccion);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CAMERA_PIC_REQUEST){
            if(resultCode==RESULT_OK){
                bitmap = (Bitmap) data.getExtras().get("data");
                ImageView iv_foto = findViewById(R.id.ivFoto);
                iv_foto.setImageBitmap(bitmap);
            }
        }
    }
}