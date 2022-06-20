package com.amtodev.hospitalReservations.admin.incidents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;

public class AdminIncidents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_incidents);
    }

    public void openAddSpecialty(View view) {
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, Add_Incidents.class);

            String token_usuario = (String) valoresAdicionales.getString("token_usuario_admin");
            String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_admin");
            String email_usuario = (String) valoresAdicionales.getString("email_usuario_admin");
            Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_admin");
            Integer id_usuario = (Integer) valoresAdicionales.getInt("id_usuario_admin");

            intent.putExtra("id_usuario_view_inc", id_usuario);
            intent.putExtra("token_usuario_view_inc", token_usuario);
            intent.putExtra("nombre_usuario_view_inc", nombre_usuario);
            intent.putExtra("email_usuario_view_inc", email_usuario);
            intent.putExtra("id_tipo_usuario_view_inc", id_tipo_usuario);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        }


    }
    public void openViewSpecialty(View view) {
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, ViewIncidents.class);

            String token_usuario = (String) valoresAdicionales.getString("token_usuario_admin");
            String nombre_usuario = (String) valoresAdicionales.getString("nombre_usuario_admin");
            String email_usuario = (String) valoresAdicionales.getString("email_usuario_admin");
            Integer id_tipo_usuario = (Integer) valoresAdicionales.getInt("id_tipo_usuario_admin");
            Integer id_usuario = (Integer) valoresAdicionales.getInt("id_usuario_admin");

            intent.putExtra("id_usuario_incidents", id_usuario);
            intent.putExtra("token_usuario_incidents", token_usuario);
            intent.putExtra("nombre_usuario_incidents", nombre_usuario);
            intent.putExtra("email_usuario_incidents", email_usuario);
            intent.putExtra("id_tipo_usuario_incidents", id_tipo_usuario);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        }
    }

}