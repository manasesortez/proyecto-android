package com.amtodev.hospitalReservations.admin.perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.user.User.AdminUser;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePerfil extends AppCompatActivity {

    String[] opciones = {
            "Posiciones",
            "Administrador",
            "Empleado"
    };

    String URL_PUT = "";
    EditText nombre, email, image;
    Spinner id_tipo_usuarios;
    Button update;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_perfil);

        nombre = findViewById(R.id.nombre_usuario_update_profile);
        email = findViewById(R.id.email_usuario_update_profile);
        id_tipo_usuarios = (Spinner) findViewById(R.id.id_tipo_usuario_update_profile);
        ArrayAdapter<String> elAdaptador = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        id_tipo_usuarios.setAdapter(elAdaptador);

        image = findViewById(R.id.image_usuario_update_profile);

        update = findViewById(R.id.btnUpdateSpecialty);

        Bundle valoresAdicionales = getIntent().getExtras();
        Integer id_usuario  = (Integer) valoresAdicionales.getInt("id_usuario_user");
        String token_usuario = (String) valoresAdicionales.getString("token_usuario_user");
        String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_user");
        String email_usuario = (String) valoresAdicionales.getString("email_usuario_user");
        Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_user");

        if (valoresAdicionales != null){
            nombre.setText(nombre_usuario);
            email.setText(email_usuario);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile("https://dinamic-api-incident.herokuapp.com/usuarios?id="+id_usuario+"&nameId=id_usuario&token="+token_usuario+"&table=usuarios&suffix=usuario");
            }
        });

    }


    private void updateProfile(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(UpdatePerfil.this);
        StringRequest request = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsePerfil(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdatePerfil.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre_usuario", nombre.getText().toString().trim());
                params.put("email_usuario", email.getText().toString().trim());
                params.put("image_usuario", image.getText().toString().trim());
                params.put("id_tipo_usuario", String.valueOf(opcionSeleccionada));
                return params;
            }

        };
        objetoPeticio.add(request);
    }

    public void parsePerfil(String jsonStr){
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);
            if (jsonObj.get("status").equals(200)){
                Intent intent = new Intent(this, Admin.class);
                startActivity(intent);
                Toast.makeText(UpdatePerfil.this, "Successfuly", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(UpdatePerfil.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(UpdatePerfil.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    public void openUpdateSpeciality(){
        startActivity(new Intent(this, Admin.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}