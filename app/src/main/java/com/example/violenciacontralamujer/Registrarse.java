package com.example.violenciacontralamujer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Registrarse extends AppCompatActivity {

    TextView login;
    EditText email,password;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        login=(TextView) findViewById(R.id.tvLogin);
        email=(EditText) findViewById(R.id.etEmail);
        password=(EditText) findViewById(R.id.etPassword);
        register=(Button) findViewById(R.id.btnRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Registrarse.this,MainActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });
    }


    public void insertarDatos(){
        final String eemail=email.getText().toString().trim();
        final String ppassword=password.getText().toString().trim();

        final ProgressDialog progressBar=new ProgressDialog(this);
        progressBar.setMessage("cargando");

        if(eemail.isEmpty()){
            email.setError("Complete los campos");
            return;
        }else{
            progressBar.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.100/violencia/insertarUsuario.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("registro correctamente")) {
                        Toast.makeText(Registrarse.this, "Datos Insertados", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();

                        Intent intent = new Intent(Registrarse.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Registrarse.this, response, Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                        Toast.makeText(Registrarse.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrarse.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params= new HashMap<>();
                    params.put("email",eemail);
                    params.put("password",ppassword);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(Registrarse.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}