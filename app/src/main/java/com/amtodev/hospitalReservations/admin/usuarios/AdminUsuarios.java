package com.amtodev.hospitalReservations.admin.usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.incidents.Add_Incidents;
import com.amtodev.hospitalReservations.admin.incidents.ViewIncidents;

public class AdminUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuarios);
    }

    public void openAddUsuario(View view) {
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, AddUsuarios.class);

            String token_usuario = (String) valoresAdicionales.getString("token_usuario_admin");
            String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_admin");
            String email_usuario = (String) valoresAdicionales.getString("email_usuario_admin");
            Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_admin");
            Integer id_usuario = (Integer) valoresAdicionales.getInt("id_usuario_admin");

            intent.putExtra("id_usuario_add_us", id_usuario);
            intent.putExtra("token_usuario_add_us", token_usuario);
            intent.putExtra("nombre_usuario_add_us", nombre_usuario);
            intent.putExtra("email_usuario_add_us", email_usuario);
            intent.putExtra("id_tipo_usuario_add_us", id_tipo_usuario);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        }
    }
    public void openViewUsuario(View view) {
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, ViewUsuarios.class);

            String token_usuario = (String) valoresAdicionales.getString("token_usuario_admin");
            String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_admin");
            String email_usuario = (String) valoresAdicionales.getString("email_usuario_admin");
            Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_admin");
            Integer id_usuario = (Integer) valoresAdicionales.getInt("id_usuario_admin");

            intent.putExtra("id_usuario_view_us", id_usuario);
            intent.putExtra("token_usuario_view_us", token_usuario);
            intent.putExtra("nombre_usuario_view_us", nombre_usuario);
            intent.putExtra("email_usuario_view_us", email_usuario);
            intent.putExtra("id_tipo_usuario_view_us", id_tipo_usuario);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        }


    }


}