package com.amtodev.hospitalReservations.admin.usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.incidents.AdminIncidents;
import com.amtodev.hospitalReservations.admin.incidents.ViewIncidents;
import com.amtodev.hospitalReservations.services.adapters.incidents;
import com.amtodev.hospitalReservations.services.adapters.usuarios;
import com.amtodev.hospitalReservations.services.helpers.DataIncidents;
import com.amtodev.hospitalReservations.services.helpers.DataUsuarios;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewUsuarios extends AppCompatActivity implements usuarios.OnUsuariosListener {

    RecyclerView lvIncidente;
    EditText CriterioSpecialty;
    Button SearchSpecialty, ExportSpecialty;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    List<Integer> arregloID = new ArrayList<Integer>();

    usuarios adapterUsuarios;
    ArrayList<DataUsuarios> listUsuarios = new ArrayList<>();


    //permisos
    private String DIRECTORIO_PDFS = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MIAPPPDFS";
    private boolean tienePermisoAlmacenamiento = false;
    private static final int CODIGO_PERMISOS_ALMACENAMIENTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_usuarios);

        SearchSpecialty = (Button) findViewById(R.id.btnSearchSpecialty);
        ExportSpecialty = (Button) findViewById(R.id.btnExportSpecialty);
        CriterioSpecialty = (EditText) findViewById(R.id.txtCriterioSpecialty);
        lvIncidente = (RecyclerView) findViewById(R.id.recyclerViewUser);

        SearchSpecialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buscar();
            }
        });
        showDataUsers("https://dinamic-api-incident.herokuapp.com/usuarios");
    }


    private void showDataUsers(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(ViewUsuarios.this);
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewUsuarios.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void parseJSON(String jsonStr){
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);
            if (jsonObj.get("status").equals(200)){
                JSONArray jsonArray = jsonObj.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject =  jsonArray.getJSONObject(i);
                    Toast.makeText(ViewUsuarios.this, "Success ", Toast.LENGTH_SHORT).show();

                    Integer id_usuario = jsonObject.getInt("id_usuario");
                    String nombre_usuario = jsonObject.getString("nombre_usuario");
                    String email_usuario = jsonObject.getString("email_usuario");
                    String password_usuario = jsonObject.getString("password_usuario");
                    String image_usuario = jsonObject.getString("image_usuario");
                    Integer id_tipo_usuario = jsonObject.getInt("id_tipo_usuario");
                    String token_usuario = jsonObject.getString("token_usuario");

                    DataUsuarios usuarios = new DataUsuarios(id_usuario, nombre_usuario, email_usuario, password_usuario, image_usuario, id_tipo_usuario, token_usuario);
                    listUsuarios.add(usuarios);
                }
                adapterUsuarios = new usuarios(ViewUsuarios.this, listUsuarios, this);
                lvIncidente.setAdapter(adapterUsuarios);
            }else {
                Toast.makeText(ViewUsuarios.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(ViewUsuarios.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //buscar();
    }

    public void openAdminViewSpecialty(View view) {
        startActivity(new Intent(this, AdminIncidents.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void verificarYPermisosDeAlmacenamiento(){
        int estadoPermiso = ContextCompat.checkSelfPermission(ViewUsuarios.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(estadoPermiso == PackageManager.PERMISSION_GRANTED){
            permisosDeAlmacenamientoConcedido();
        }else{
            //si no, entonces pedimos permisos, ahora mira onRequestPermissionResult
            ActivityCompat.requestPermissions(ViewUsuarios.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_PERMISOS_ALMACENAMIENTO);
        }
    }

    private void permisosDeAlmacenamientoConcedido(){
        File carpeta = new File(DIRECTORIO_PDFS);
        if (!carpeta.exists()){
            carpeta.mkdir();
        }else{
            Log.i("Speacialty", "Filed Created Successfully");
        }
        tienePermisoAlmacenamiento = true;
    }

    //Solicitud de Permisos
    private void permisosDeAlmacenamientoDenegado(){
        Toast.makeText(ViewUsuarios.this, "Permission for storage is denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_PERMISOS_ALMACENAMIENTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permisosDeAlmacenamientoConcedido();
                }else{
                    permisosDeAlmacenamientoDenegado();
                }
                break;
        }
    }

    @Override
    public void onUsuariosClick(int position) {

    }

}