package com.amtodev.hospitalReservations;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.incidents.Add_Incidents;
import com.amtodev.hospitalReservations.admin.incidents.AdminIncidents;
import com.amtodev.hospitalReservations.admin.usuarios.AdminUsuarios;
import com.amtodev.hospitalReservations.user.User.AdminUser;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    Boolean valid = true;
    //FirebaseAuth fAuth;
    //FirebaseFirestore fStore;


    @SuppressLint({"CutPasteId", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editTextEmailLogin);
        password = (EditText) findViewById(R.id.editTextPasswordLogin);
        loginBtn = (Button) findViewById(R.id.cirLoginButtonLogin);

        loginBtn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkField(email);
                checkField(password);
                //onFogetPasswordClick(view);
                jsonParse();
            }
        });
    }

    private void jsonParse() {
            RequestQueue objetoPeticio = Volley.newRequestQueue(Login.this);
            StringRequest request = new StringRequest(Request.Method.POST,"https://dinamic-api-incident.herokuapp.com/usuarios?login=true&suffix=usuario",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseJSON(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this, "error - Status 400 Bad " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        public Map<String, String> getHeaders(){
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("Authorization", "c5LTA6WPbMwHhEabYu77nN9cn4VcMj");
                            return map;
                        }

                        public Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email_usuario", email.getText().toString().trim());
                            params.put("password_usuario", password.getText().toString().trim());
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
                JSONArray jsonArray = jsonObj.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject =  jsonArray.getJSONObject(i);
                    if(jsonObject.get("id_tipo_usuario").equals(1)){

                        Intent intent = new Intent(this, Admin.class);

                        String token_usuario = (String) jsonObject.get("token_usuario");
                        String nombre_usuario = (String) jsonObject.get("nombre_usuario");
                        String email_usuario = (String) jsonObject.get("email_usuario");
                        Integer id_tipo_usuario = (Integer) jsonObject.get("id_tipo_usuario");
                        Integer id_usuario = (Integer) jsonObject.get("id_usuario");

                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("token_usuario", token_usuario);
                        intent.putExtra("nombre_usuario", nombre_usuario);
                        intent.putExtra("email_usuario", email_usuario);
                        intent.putExtra("id_tipo_usuario", id_tipo_usuario);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Welcome Login", Toast.LENGTH_SHORT).show();

                        finish();

                    }if(jsonObject.get("id_tipo_usuario").equals(2)){

                        Intent intent = new Intent(this, AdminUser.class);
                        String token_usuario = (String) jsonObject.get("token_usuario");
                        String nombre_usuario = (String) jsonObject.get("nombre_usuario");
                        String email_usuario = (String) jsonObject.get("email_usuario");
                        Integer id_tipo_usuario = (Integer) jsonObject.get("id_tipo_usuario");
                        Integer id_usuario = (Integer) jsonObject.get("id_usuario");

                        intent.putExtra("id_usuario", id_usuario);
                        intent.putExtra("token_usuario", token_usuario);
                        intent.putExtra("nombre_usuario", nombre_usuario);
                        intent.putExtra("email_usuario", email_usuario);
                        intent.putExtra("id_tipo_usuario", id_tipo_usuario);

                        startActivity(intent);
                        finish();
                    }
                }
            }else{
                Toast.makeText(Login.this, "Failed Request" + jsonObj.toString() , Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(Login.this, "Error - Status 400 " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void onLoginClick(View View){
        startActivity(new Intent(this,Register.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

    public void onFogetPasswordClick(View View){
        startActivity(new Intent(this,Admin.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }


    public boolean checkField(EditText textfield){
        if(textfield.getText().toString().isEmpty()){
            textfield.setError("Error");
            valid = false;
        }else{
            valid = true;
        }
        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}