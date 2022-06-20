package com.amtodev.hospitalReservations.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Login;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.incidents.AdminIncidents;
import com.amtodev.hospitalReservations.admin.incidents.UpdateIncidents;
import com.amtodev.hospitalReservations.admin.perfil.ViewPerfil;
import com.amtodev.hospitalReservations.admin.usuarios.AdminUsuarios;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {

    int especialidad_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        CardView logoutAdmin = findViewById(R.id.cv_luminosidad);
        logoutAdmin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });
    }

    public void logoutAdmin(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void openDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you Sure to Logout");
        builder.setPositiveButton("Yes, Logout", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logoutAdmin();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Admin.this, "No Logout", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    public void openAdminSpecialty(View View){
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, AdminIncidents.class);

            String token_usuarioAdmin = (String) valoresAdicionales.getString("token_usuario");
            String nombre_usuarioAdmin = (String) valoresAdicionales.getString("nombre_usuario");
            String email_usuarioAdmin = (String) valoresAdicionales.getString("email_usuario");
            Integer id_tipo_usuarioAdmin = (Integer) valoresAdicionales.getInt("id_tipo_usuario");
            Integer id_usuarioAdmin = (Integer) valoresAdicionales.getInt("id_usuario");

            intent.putExtra("id_usuario_admin", id_usuarioAdmin);
            intent.putExtra("token_usuario_admin", token_usuarioAdmin);
            intent.putExtra("nombre_usuario_admin", nombre_usuarioAdmin);
            intent.putExtra("email_usuario_admin", email_usuarioAdmin);
            intent.putExtra("id_tipo_usuario_admin", id_tipo_usuarioAdmin);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
    }

    public void openAdminProfile(View View){
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, ViewPerfil.class);

            String token_usuarioAdmin = (String) valoresAdicionales.getString("token_usuario");
            String nombre_usuarioAdmin = (String) valoresAdicionales.getString("nombre_usuario");
            String email_usuarioAdmin = (String) valoresAdicionales.getString("email_usuario");
            Integer id_tipo_usuarioAdmin = (Integer) valoresAdicionales.getInt("id_tipo_usuario");
            Integer id_usuarioAdmin = (Integer) valoresAdicionales.getInt("id_usuario");

            intent.putExtra("id_usuario_admin", id_usuarioAdmin);
            intent.putExtra("token_usuario_admin", token_usuarioAdmin);
            intent.putExtra("nombre_usuario_admin", nombre_usuarioAdmin);
            intent.putExtra("email_usuario_admin", email_usuarioAdmin);
            intent.putExtra("id_tipo_usuario_admin", id_tipo_usuarioAdmin);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }

    }

    public void openAdminUsers(View View){
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, AdminUsuarios.class);

            String token_usuarioAdmin = (String) valoresAdicionales.getString("token_usuario");
            String nombre_usuarioAdmin = (String) valoresAdicionales.getString("nombre_usuario");
            String email_usuarioAdmin = (String) valoresAdicionales.getString("email_usuario");
            Integer id_tipo_usuarioAdmin = (Integer) valoresAdicionales.getInt("id_tipo_usuario");
            Integer id_usuarioAdmin = (Integer) valoresAdicionales.getInt("id_usuario");

            intent.putExtra("id_usuario_admin", id_usuarioAdmin);
            intent.putExtra("token_usuario_admin", token_usuarioAdmin);
            intent.putExtra("nombre_usuario_admin", nombre_usuarioAdmin);
            intent.putExtra("email_usuario_admin", email_usuarioAdmin);
            intent.putExtra("id_tipo_usuario_admin", id_tipo_usuarioAdmin);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
    }

}