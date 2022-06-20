
package com.amtodev.hospitalReservations.admin.usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import com.amtodev.hospitalReservations.admin.incidents.Add_Incidents;
import com.amtodev.hospitalReservations.admin.incidents.AdminIncidents;
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

public class AddUsuarios extends AppCompatActivity {

    String[] opciones = {
            "Posiciones",
            "Administrador",
            "Empleado"
    };

    String URL_PUT = "";

    EditText nombre_usuario, email_usuario, poassword_usuario, image_usuario;
    Spinner id_tipo_usuarios;
    Button btnAddUser;

    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usuarios);

        nombre_usuario = findViewById(R.id.nombre_usuario);
        email_usuario = findViewById(R.id.email_usuario);
        poassword_usuario = findViewById(R.id.password_usuario);
        id_tipo_usuarios = findViewById(R.id.id_tipo_usuarioss);
        ArrayAdapter<String> elAdaptador = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        id_tipo_usuarios.setAdapter(elAdaptador);

        image_usuario = findViewById(R.id.image_usuario);
        btnAddUser  = (Button) findViewById(R.id.btnAddUser);

        Bundle valoresAdicionales = getIntent().getExtras();
        Integer id_usuario  = (Integer) valoresAdicionales.getInt("id_usuario_add_us");
        String token_usuario = (String) valoresAdicionales.getString("token_usuario_add_us");
        String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_add_us");
        String email_usuario = (String) valoresAdicionales.getString("email_usuario_add_us");
        Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_add_us");


        btnAddUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addUser("https://dinamic-api-incident.herokuapp.com/usuarios?token="+token_usuario+"&table=usuarios&suffix=usuario&except");
            }
        });

    }

    private void addUser(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(AddUsuarios.this);
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
                        Toast.makeText(AddUsuarios.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                opcionSeleccionada = id_tipo_usuarios.getSelectedItemPosition();

                Bundle valoresAdicionales = getIntent().getExtras();
                Integer id_usuario  = (Integer) valoresAdicionales.getInt("id_usuario_add_us");

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre_usuario", nombre_usuario.getText().toString().trim());
                params.put("email_usuario", email_usuario.getText().toString().trim());
                params.put("password_usuario", poassword_usuario.getText().toString().trim());
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
                Toast.makeText(AddUsuarios.this, "Successfuly", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(AddUsuarios.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(AddUsuarios.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void openAdminSpecialty(View view) {
        startActivity(new Intent(this, AdminIncidents.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }
}