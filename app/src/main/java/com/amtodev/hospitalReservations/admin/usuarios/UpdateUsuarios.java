package com.amtodev.hospitalReservations.admin.usuarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.incidents.AdminIncidents;
import com.amtodev.hospitalReservations.admin.incidents.UpdateIncidents;
import com.amtodev.hospitalReservations.admin.incidents.ViewIncidents;
import com.amtodev.hospitalReservations.services.helpers.DataIncidents;
import com.amtodev.hospitalReservations.services.helpers.DataUsuarios;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class UpdateUsuarios extends AppCompatActivity {

    String[] opciones = {
            "Posiciones",
            "Administrador",
            "Empleado"
    };

    EditText nombre_usuario, email_usuario, password_usuario, image_usuario;
    Button Button_SpecialtyUpdate, Button_SpecialtyDelete;
    Spinner id_tipo_usuario;
    int especialidad_id;

    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_usuarios);

        nombre_usuario = findViewById(R.id.nombre_usuario_update);
        email_usuario = findViewById(R.id.email_usuario_update);
        image_usuario = findViewById(R.id.image_usuario_update);

        id_tipo_usuario = findViewById(R.id.id_tipo_usuario_update);
        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        id_tipo_usuario.setAdapter(Adapter2);

        Button_SpecialtyUpdate = (Button) findViewById(R.id.btnUpdateUsuario);
        Button_SpecialtyDelete = (Button) findViewById(R.id.btnDeleteUsuario);

        Button_SpecialtyUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre_usuario.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateUsuarios.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }if (email_usuario.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateUsuarios.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }if (password_usuario.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateUsuarios.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }if (image_usuario.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateUsuarios.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    nombre_usuario.getText().clear();
                    email_usuario.getText().clear();
                    password_usuario.getText().clear();
                    image_usuario.getText().clear();

                    DataUsuarios adap_edit = (DataUsuarios) getIntent().getSerializableExtra("edit-user");
                    String token_usuario = adap_edit.getToken_usuario();
                    Integer id = adap_edit.getId_usuario();
                    updateUsuarios("https://dinamic-api-incident.herokuapp.com/usuarios?id="+id+"&nameId=id_usuario&token="+token_usuario+"&table=usuarios&suffix=usuario");

                }
            }
        });
        Button_SpecialtyDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUsuarios.this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to delete this InterHospital?");
        builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataUsuarios adap_edit = (DataUsuarios) getIntent().getSerializableExtra("edit-user");
                String token_usuario = adap_edit.getToken_usuario();
                Integer id = adap_edit.getId_usuario();
                deleteUsuario("https://dinamic-api-incident.herokuapp.com/usuarios?id="+id+"&nameId=id_usuario&token="+token_usuario+"&table=usuarios&suffix=usuario");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateUsuarios.this, "InterHospital no Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void deleteUsuario(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(UpdateUsuarios.this);
        StringRequest request = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateUsuarios.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            public Map<String, String> getHeaders(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "c5LTA6WPbMwHhEabYu77nN9cn4VcMj");
                return map;
            }
        };
        objetoPeticio.add(request);
    }


    private void updateUsuarios(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(UpdateUsuarios.this);
        StringRequest request = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateUsuarios.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            public Map<String, String> getHeaders(){
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "c5LTA6WPbMwHhEabYu77nN9cn4VcMj");
                return map;
            }

            public Map<String, String> getParams(){
                int opcionSeleccionada;
                opcionSeleccionada = id_tipo_usuario.getSelectedItemPosition();

                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre_usuario", nombre_usuario.getText().toString().trim());
                params.put("email_usuario", email_usuario.getText().toString().trim());
                params.put("image_usuario", image_usuario.getText().toString().trim());
                params.put("id_tipo_usuario", String.valueOf(opcionSeleccionada));
                return params;
            }

        };
        objetoPeticio.add(request);
    }

    public void parseJSON(String jsonStr){
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);
            if (jsonObj.get("status").equals(200)){
                Intent intent = new Intent(this, AdminUsuarios.class);
                startActivity(intent);
                Toast.makeText(UpdateUsuarios.this, "Successfuly", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(UpdateUsuarios.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(UpdateUsuarios.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void GoBackActivity(){
        Intent actividad = new Intent(UpdateUsuarios.this, ViewIncidents.class);
        startActivity(actividad);
        UpdateUsuarios.this.finish();
    }

    public void openUpdateSpeciality(View view) {
        startActivity(new Intent(this, ViewIncidents.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales == null){
            Toast.makeText(UpdateUsuarios.this, "You need send some InterHospital ID", Toast.LENGTH_SHORT).show();
            especialidad_id = 0;
            GoBackActivity();
        }else{
            especialidad_id = valoresAdicionales.getInt("especialidad_id");
        }
    }

}