package com.example.violenciacontralamujer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class DenunciaActivity extends AppCompatActivity {

    EditText ci,declaracion;
    Spinner spinner;
    Button registrar;
    String sci,stipo,sdeclaracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        ci=(EditText)findViewById(R.id.etCi);
        declaracion=(EditText) findViewById(R.id.etDeclaracion);
        registrar=(Button) findViewById(R.id.btnInsertarDenuncia);

        spinner=(Spinner) findViewById(R.id.spSpinner);
        String[] opciones={"Violencia física","Violencia sexual","Violencia psicológica","Violencia emocional","Violencia económica","Otro"};
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item_jose,opciones);
        spinner.setAdapter(adapter);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sci=ci.getText().toString().trim();
                stipo=spinner.getSelectedItem().toString().trim();
                sdeclaracion=declaracion.getText().toString().trim();
                if(!sci.isEmpty() && !sdeclaracion.isEmpty()){
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.1.104/violencia/insertarDenuncia.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Registros insertados", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),AlertaActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DenunciaActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> parametros=new HashMap<String,String>();
                            parametros.put("ci",sci);
                            parametros.put("tipoDeDenuncia",stipo);
                            parametros.put("declaracion",sdeclaracion);
                            return parametros;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(DenunciaActivity.this);
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(DenunciaActivity.this,"No se permite campos vacios",Toast.LENGTH_SHORT).show();
                }
            }

        });





    }
}