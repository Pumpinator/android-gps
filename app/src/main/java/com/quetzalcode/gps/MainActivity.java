package com.quetzalcode.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.net.Uri;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText txtLatitud, txtLongitud, txtAltitud;
    Button btnLocalizar, btnGmail, btnMensaje, btnSalir;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarControles();
    }

    public void iniciarControles() {
        txtLatitud = (EditText) findViewById(R.id.txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.txtLongitud);
        txtAltitud = (EditText) findViewById(R.id.txtAltitud);
        btnLocalizar = (Button) findViewById(R.id.btnLocalizar);
        btnGmail = (Button) findViewById(R.id.btnGmail);
        btnMensaje = (Button) findViewById(R.id.btnMensaje);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        //------------------------Administrador y escucha GPS-----------------------------
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener Ll = new MilocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Ll);
    }
    //----------------clase MiLocationListener para activar el escucha de java----
    public class MilocationListener implements LocationListener{
        @Override
        public void onLocationChanged(@NonNull Location location) {
            txtLatitud.setText(String.valueOf(location.getLatitude()));
            txtLongitud.setText(String.valueOf(location.getLongitude()));
            txtAltitud.setText(String.valueOf(location.getAltitude()));
        }
    }

    public void abrirOtra(View V){
        String url=String.format(txtLatitud.getText().toString()+","+ txtLongitud.getText().toString());
        Intent i=new Intent(this, Otra.class);
        i.putExtra("ruta",url);
        startActivity(i);
    }

    public void enviarCorreo(View view) {
        String latitud = txtLatitud.getText().toString();
        String longitud = txtLongitud.getText().toString();
        String altitud = txtAltitud.getText().toString();

        // Dirección de correo electrónico a la que quieres enviar el correo
        String destinatario = "80085@alumnos.utleon.edu.mx";

        // Asunto del correo
        String asunto = "Datos de ubicación";

        // Cuerpo del correo
        String cuerpo = "Latitud: " + latitud + "\n"
                + "Longitud: " + longitud + "\n"
                + "Altitud: " + altitud;

        // Crear un intento para enviar el correo
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + destinatario));
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, cuerpo);

        // Verificar si hay una aplicación de correo electrónico disponible
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Si no hay ninguna aplicación de correo electrónico disponible, muestra un mensaje de error
            Toast.makeText(this, "No se encontró ninguna aplicación de correo electrónico.", Toast.LENGTH_SHORT).show();
        }
    }

}