package com.amtodev.hospitalReservations.admin.incidents;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.amtodev.hospitalReservations.services.adapters.incidents;
import com.amtodev.hospitalReservations.services.helpers.DataIncidents;
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

public class UpdateIncidents extends AppCompatActivity {

    String[] estado_arr = {
            "Estados: ",
            "Incidente Activo",
            "Incidente Resuelto"
    };

    String[] tipoIncidente = {
            "Tipo Incidente: ",
            "Ataques cibernéticos",
            "Código malicioso",
            "Denegación de Servicio",
            "Denegación de Servicio Distribuida",
            "Acceso no autorizado",
            "Pérdida de datos",
            "Daños físicos",
            "Abuso de privilegios",
    };


    EditText  descripcions, imagen_incidente;
    Spinner tipo_incidente, estado_incidente;
    Button Button_IncidentUpdate, Button_IncidentDelete;
    int especialidad_id;

    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;
    
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_incidents);

        tipo_incidente = findViewById(R.id.tipo_incidente_update_incidents);
        ArrayAdapter<String> Adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, tipoIncidente);
        tipo_incidente.setAdapter(Adapter1);

        descripcions = findViewById(R.id.descripcion_update_incidents);
        estado_incidente = findViewById(R.id.estado_incidente_update_incidents);
        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, estado_arr);
        estado_incidente.setAdapter(Adapter2);

        imagen_incidente = findViewById(R.id.imagen_incidente_update_incidents);

        Button_IncidentUpdate = (Button) findViewById(R.id.btnUpdateIncidente);
        Button_IncidentDelete = (Button) findViewById(R.id.btnDeleteIncident);

        Button_IncidentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descripcions.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateIncidents.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                }if (imagen_incidente.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateIncidents.this, "Don't leave empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    DataIncidents adap_edit = (DataIncidents) getIntent().getSerializableExtra("edit");
                    String token_usuario = adap_edit.getToken_usuario();
                    Integer id = adap_edit.getId_incidente();
                    updateIncident("https://dinamic-api-incident.herokuapp.com/incidentes?id="+id+"&nameId=id_incidente&token="+token_usuario+"&table=usuarios&suffix=usuario");
                }
            }
        });

        Button_IncidentDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateIncidents.this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to delete this InterHospital?");
        builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataIncidents adap_edit = (DataIncidents) getIntent().getSerializableExtra("edit");
                String token_usuario = adap_edit.getToken_usuario();
                Integer id = adap_edit.getId_incidente();
                deleteIncident("https://dinamic-api-incident.herokuapp.com/incidentes?id="+id+"&nameId=id_incidente&token="+token_usuario+"&table=usuarios&suffix=usuario");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateIncidents.this, "InterHospital no Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void deleteIncident(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(UpdateIncidents.this);
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
                        Toast.makeText(UpdateIncidents.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void updateIncident(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(UpdateIncidents.this);
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
                        Toast.makeText(UpdateIncidents.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                opcionSeleccionada = tipo_incidente.getSelectedItemPosition();

                int selected2;
                selected2 = estado_incidente.getSelectedItemPosition();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                DataIncidents adap_edit = (DataIncidents) getIntent().getSerializableExtra("edit");

                Map<String, String> params = new HashMap<String, String>();
                params.put("id_tipo_incidente", String.valueOf(opcionSeleccionada));
                params.put("descripcion_incidente", descripcions.getText().toString().trim());
                params.put("fecha_incidente", String.valueOf(now));
                params.put("id_usuario_incidente", String.valueOf(adap_edit.getId_usuario_incidente()));
                params.put("imagen_incidente", imagen_incidente.getText().toString().trim());
                params.put("id_estado_incidente", String.valueOf(selected2));
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
                Intent intent = new Intent(this, AdminIncidents.class);
                startActivity(intent);
                Toast.makeText(UpdateIncidents.this, "Successfuly", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(UpdateIncidents.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(UpdateIncidents.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void GoBackActivity(){
        Intent actividad = new Intent(UpdateIncidents.this, ViewIncidents.class);
        startActivity(actividad);
        UpdateIncidents.this.finish();
    }

    public void openUpdateSpeciality(View view) {
        startActivity(new Intent(this, ViewIncidents.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void delete(){
        try{
            Toast.makeText(UpdateIncidents.this, "InterHospital successfully Delete", Toast.LENGTH_SHORT).show();
        }catch (Exception error){
            Toast.makeText(UpdateIncidents.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        DataIncidents adap_edit = (DataIncidents) getIntent().getSerializableExtra("edit");
        if(adap_edit != null){
            Button_IncidentUpdate.setText("Actualizar");
            descripcions.setText(adap_edit.getDescripcion_incidente());
            imagen_incidente.setText(adap_edit.getImagen_incidente());
        }
    }


}