package com.example.violenciacontralamujer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.violenciacontralamujer.Model.ContactosModel;

import java.util.ArrayList;

public class Contactos extends AppCompatActivity{

    EditText etcontacto,ettelefono;
    ImageButton contact;
    static final int PICK_CONTACT_REQUEST=1;
    Button guardar,continuar;
    Button eliminarLista;
    ListView lvListaContactos;


    AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(Contactos.this,"administracion",null,1);

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        etcontacto=(EditText) findViewById(R.id.etContacto);
        ettelefono=(EditText) findViewById(R.id.etTelefono);
        contact=(ImageButton) findViewById(R.id.ibContacto);
        guardar=(Button) findViewById(R.id.btnGuardar);
        lvListaContactos=(ListView)findViewById(R.id.lvListaContactos);
        eliminarLista=(Button) findViewById(R.id.btnEliminarTabla);
        continuar=(Button) findViewById(R.id.btnContinuar);



        //Click para segui avanzando de contactos a alerta
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Contactos.this,AlertaActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Selecciona un contacto del celular
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarContacto();
            }
        });


        //Elimina la lista de contactos
        eliminarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = admin.getWritableDatabase();
                db.delete("contactos",null,null);
                db.execSQL("delete from "+ "contactos");
                Toast.makeText(Contactos.this,"La lista a sido limpiado",Toast.LENGTH_SHORT).show();
                db.close();
                finish();
                startActivity(getIntent());
            }
        });




        //Click para registrar los contactos en SQLite
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(Contactos.this,"administracion",null,1);
                SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();//Abrir modo de lectura y escritura la bdd

                String contacto=etcontacto.getText().toString();//obtenemos el valor de los edittext
                String telefono=ettelefono.getText().toString();

                if(!contacto.isEmpty() && !telefono.isEmpty()){//condicion para no dejar vacio los campos de contactos
                    ContentValues registro= new ContentValues();
                    registro.put("nombre",contacto);//guardamos dentro de la bdd ahora falta insertar en la tabla
                    registro.put("numero",telefono);

                    BaseDeDatos.insert("contactos",null,registro);//registramos a la tabla
                    BaseDeDatos.close();//Cerramos la base de datos;
                    etcontacto.setText("");
                    ettelefono.setText("");
                    Toast.makeText(Contactos.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                    listarContactos();
                }else{
                    Toast.makeText(Contactos.this,"Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        listarContactos();
    }

    private ArrayList<ContactosModel> obtenerContactos() {
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();
        //select * from contactos
        Cursor cursor= BaseDeDatos.rawQuery("SELECT * FROM contactos",null);
        ArrayList<ContactosModel> lista=new ArrayList<ContactosModel>();


        while(cursor.moveToNext()){
            ContactosModel cm=new ContactosModel();
            cm.setId(cursor.getInt(0));
            cm.setContacto(cursor.getString(1));
            cm.setTelefono(cursor.getString(2));
            lista.add(cm);
        }
        BaseDeDatos.close();
        return lista;
    }

    public void listarContactos(){
        ArrayList<ContactosModel> lista= obtenerContactos();
        if(!lista.isEmpty()){
            ArrayAdapter<ContactosModel> adaptador = new ArrayAdapter<ContactosModel>(Contactos.this, android.R.layout.simple_list_item_1,lista);
            lvListaContactos.setAdapter(adaptador);
        }
    }





    //Metodo para seleccionar los contactos del telefono
    private void SeleccionarContacto() {
        Intent selectContactItem=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        selectContactItem.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(selectContactItem,PICK_CONTACT_REQUEST);
    }


    //Metodo sobreescrito cuando acabemos de seleccionar el contacto del telefono
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                if(cursor.moveToFirst()){
                    int columnaNombre=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int columnaNumero=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String nombre=cursor.getString(columnaNombre);
                    String numero=cursor.getString(columnaNumero);
                    etcontacto.setText(nombre);
                    ettelefono.setText(numero);

                }
            }
        }
    }





}