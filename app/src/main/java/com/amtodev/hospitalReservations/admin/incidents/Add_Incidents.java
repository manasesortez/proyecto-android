package com.amtodev.hospitalReservations.admin.incidents;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.perfil.UpdatePerfil;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Add_Incidents extends AppCompatActivity {

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

    Spinner tipoIncident, estado;
    EditText txtDescripcion, imageIncident;
    Button btn_AddIncident;
    String URL = "";

    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incidents);

        tipoIncident = findViewById(R.id.tipoIncidente);
        ArrayAdapter<String> Adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, tipoIncidente);
        tipoIncident.setAdapter(Adapter1);

        estado = findViewById(R.id.estadoIncidente);
        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, estado_arr);
        estado.setAdapter(Adapter2);

        txtDescripcion = findViewById(R.id.txtDescription);
        imageIncident = findViewById(R.id.imageIncident);

        btn_AddIncident = findViewById(R.id.btnSaveIncident);

        Bundle valoresAdicionales = getIntent().getExtras();
        Integer id_usuario  = (Integer) valoresAdicionales.getInt("id_usuario_view_inc");
        String token_usuario = (String) valoresAdicionales.getString("token_usuario_view_inc");
        String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_view_inc");
        String email_usuario = (String) valoresAdicionales.getString("email_usuario_view_inc");
        Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_view_inc");

        btn_AddIncident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIncident("https://dinamic-api-incident.herokuapp.com/incidentes?token="+token_usuario+"&table=usuarios&suffix=usuario&except");
            }
        });

    }


    private void addIncident(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(Add_Incidents.this);
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_Incidents.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                opcionSeleccionada = tipoIncident.getSelectedItemPosition();

                int selected2;
                selected2 = estado.getSelectedItemPosition();

                Bundle valoresAdicionales = getIntent().getExtras();
                Integer id_usuario  = (Integer) valoresAdicionales.getInt("id_usuario_view_inc");

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                Map<String, String> params = new HashMap<String, String>();
                params.put("id_tipo_incidente", String.valueOf(opcionSeleccionada));
                params.put("descripcion_incidente", txtDescripcion.getText().toString().trim());
                params.put("fecha_incidente", String.valueOf(now));
                params.put("id_usuario_incidente", String.valueOf(id_usuario));
                params.put("imagen_incidente", imageIncident.getText().toString().trim());
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
                Toast.makeText(Add_Incidents.this, "Successfuly", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(Add_Incidents.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(Add_Incidents.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void openAdminSpecialty(View view) {
        startActivity(new Intent(this, AdminIncidents.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

}