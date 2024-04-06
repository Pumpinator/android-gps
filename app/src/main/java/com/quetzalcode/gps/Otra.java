package com.quetzalcode.gps;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Otra extends AppCompatActivity {
    WebView wvOtra;
    EditText txtPosicion;
    Button btnUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        txtPosicion =(findViewById(R.id.txtPosicion));
        btnUbicacion =(Button)findViewById(R.id.btnUbicacion);
        wvOtra = (WebView) findViewById(R.id.wvOtra);
        wvOtra.getSettings().setJavaScriptEnabled(true);
        wvOtra.setWebViewClient(new WebViewClient());
        //-------------------------obtener el extra de MainActivity-----------
        Bundle bun=getIntent().getExtras();
        txtPosicion.setText(bun.getString("ruta"));
    }
    public void ir(View v){
        wvOtra.loadUrl("https://www.google.com.mx/maps/place/"+this.txtPosicion.getText().toString());
    }
}

