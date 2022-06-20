package com.amtodev.hospitalReservations.admin.incidents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.usuarios.AddUsuarios;
import com.amtodev.hospitalReservations.admin.usuarios.AdminUsuarios;
import com.amtodev.hospitalReservations.services.adapters.incidents;
import com.amtodev.hospitalReservations.services.helpers.ArrayUtil;
import com.amtodev.hospitalReservations.services.helpers.DataIncidents;
import com.amtodev.hospitalReservations.user.User.AdminUser;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewIncidents extends AppCompatActivity implements incidents.OnIncidentsListener {

    RecyclerView lvIncidente;
    EditText CriterioSpecialty;
    Button SearchSpecialty, ExportSpecialty;
    incidents adapterIncidents;
    ArrayList<DataIncidents> listdata = new ArrayList<>();

    ArrayAdapter adaptador;
    List<Integer> arregloID = new ArrayList<Integer>();

    //permisos
    private String DIRECTORIO_PDFS = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MIAPPPDFS";
    private boolean tienePermisoAlmacenamiento = false;
    private static final int CODIGO_PERMISOS_ALMACENAMIENTO = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_incidents);
        verificarYPermisosDeAlmacenamiento();

        SearchSpecialty = (Button) findViewById(R.id.btnSearchSpecialty);
        ExportSpecialty = (Button) findViewById(R.id.btnExportSpecialty);
        CriterioSpecialty = (EditText) findViewById(R.id.txtCriterioSpecialty);
        lvIncidente = (RecyclerView) findViewById(R.id.recyclerViewIncidents);

        SearchSpecialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buscar();
            }
        });
        showDataIncidents("https://dinamic-api-incident.herokuapp.com/relations?rel=incidentes,usuarios&type=incidente,usuario&select=id_incidente,id_tipo_incidente,descripcion_incidente,fecha_incidente,id_usuario_incidente,nombre_usuario,token_usuario,imagen_incidente,id_estado_incidente&orderBy=id_incidente&orderMode=ASC&startAt=0&endAt=100");
    }

    private void showDataIncidents(String URL) {
        RequestQueue objetoPeticio = Volley.newRequestQueue(ViewIncidents.this);
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
                        Toast.makeText(ViewIncidents.this, "error - Status 400 Bad Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ViewIncidents.this, "Success ", Toast.LENGTH_SHORT).show();

                    Integer id_incidente = jsonObject.getInt("id_incidente");
                    Integer id_tipo_incidente = jsonObject.getInt("id_tipo_incidente");
                    String descripcion_incidente = jsonObject.getString("descripcion_incidente");
                    String fecha_incidente = jsonObject.getString("fecha_incidente");
                    Integer id_usuario_incidente = jsonObject.getInt("id_usuario_incidente");
                    String imagen_incidente = jsonObject.getString("imagen_incidente");
                    Integer id_estado_incidente = jsonObject.getInt("id_estado_incidente");
                    String nombre_usuario = jsonObject.getString("nombre_usuario");
                    String token_usuario = jsonObject.getString("token_usuario");

                    DataIncidents incidents = new DataIncidents(id_incidente, id_tipo_incidente, descripcion_incidente, fecha_incidente, id_usuario_incidente, imagen_incidente, id_estado_incidente, nombre_usuario, token_usuario);
                    listdata.add(incidents);
                }
                adapterIncidents = new incidents(ViewIncidents.this, listdata, this);
                lvIncidente.setAdapter(adapterIncidents);
            }else {
                Toast.makeText(ViewIncidents.this, "Failed Request" + jsonObj.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(ViewIncidents.this, "Error - Status 400" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /*
    private void buscar() {
        //lista = llenarLista();
        adaptador = new ArrayAdapter(ViewIncidents.this, R.layout.list_black_text, lista);
        lvIncidente.setAdapter(adaptador);
    }*/

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
        int estadoPermiso = ContextCompat.checkSelfPermission(ViewIncidents.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(estadoPermiso == PackageManager.PERMISSION_GRANTED){
            permisosDeAlmacenamientoConcedido();
        }else{
            //si no, entonces pedimos permisos, ahora mira onRequestPermissionResult
            ActivityCompat.requestPermissions(ViewIncidents.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_PERMISOS_ALMACENAMIENTO);
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
        Toast.makeText(ViewIncidents.this, "Permission for storage is denied", Toast.LENGTH_SHORT).show();
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
    public void onIncidentsClick(int position) {

    }
}