package com.example.violenciacontralamujer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertarDenunciante extends AppCompatActivity {

    EditText etCi,etNombre,etApellidoPaterno,etApellidoMaterno,etTelefono;
    Button insertarDatos;
    String ci,nombre,apellidoPaterno,apellidoMaterno,telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_denunciante);

        etCi=(EditText) findViewById(R.id.etCi);
        etNombre=(EditText) findViewById(R.id.etNombre);
        etApellidoPaterno=(EditText) findViewById(R.id.etApellidoPaterno);
        etApellidoMaterno=(EditText) findViewById(R.id.etApellidoMaterno);
        etTelefono=(EditText) findViewById(R.id.etTelefono);
        insertarDatos=(Button) findViewById(R.id.btnInsertarDatos);

        insertarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos("http://192.168.1.104/violencia/insertarDenunciante.php");
            }
        });

    }


    private void insertarDatos(String URL){

        ci=etCi.getText().toString().trim();
        nombre=etNombre.getText().toString().trim();
        apellidoPaterno=etApellidoPaterno.getText().toString().trim();
        apellidoMaterno=etApellidoMaterno.getText().toString().trim();
        telefono=etTelefono.getText().toString().trim();

        //if(ci.isEmpty() && nombre.isEmpty() && apellidoPaterno.isEmpty() && apellidoMaterno.isEmpty() && telefono.isEmpty()){
        //    Toast.makeText(getApplicationContext(), "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
        //}else{
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Registros insertados", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),Contactos.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros= new HashMap<String, String>();
                    parametros.put("ci",ci);
                    parametros.put("nombre",nombre);
                    parametros.put("apellidoPaterno",apellidoPaterno);
                    parametros.put("apellidoMaterno",apellidoMaterno);
                    parametros.put("telefono",telefono);
                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        //}
    }






}