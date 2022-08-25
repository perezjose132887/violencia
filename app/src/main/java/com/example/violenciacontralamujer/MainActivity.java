package com.example.violenciacontralamujer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    Button register,login;
    EditText etEmail,etPassword;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(Button) findViewById(R.id.btnRegister);
        login=(Button) findViewById(R.id.btnLogin);
        etEmail=(EditText) findViewById(R.id.etEmail);
        etPassword=(EditText) findViewById(R.id.etPassword);
        recuperarPrefencias();


        //validamos el email y password en el login()
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=etEmail.getText().toString().trim();
                password=etPassword.getText().toString().trim();
                if(!email.isEmpty() && !password.isEmpty()){
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.1.100/violencia/verificarLogin.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.isEmpty()){
                                guardarPreferencias();
                                Intent intent=new Intent(getApplicationContext(),InsertarDenunciante.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> parametros=new HashMap<String,String>();
                            parametros.put("email",email);
                            parametros.put("password",password);
                            return parametros;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(MainActivity.this,"No se permite campos vacios",Toast.LENGTH_SHORT).show();
                }
            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Registrarse.class);
                startActivity(intent);
            }
        });
    }


    private void guardarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    private void recuperarPrefencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        etEmail.setText(preferences.getString("email","micorreo@gmail.com"));
        etPassword.setText(preferences.getString("password","123456"));
    }



}