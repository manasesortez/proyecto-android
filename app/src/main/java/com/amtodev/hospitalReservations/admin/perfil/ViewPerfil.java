package com.amtodev.hospitalReservations.admin.perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.usuarios.AdminUsuarios;

public class ViewPerfil extends AppCompatActivity {

    TextView name, email, position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_perfil);
        name = findViewById(R.id.nameInfo);
        email = findViewById(R.id.email);
        position = findViewById(R.id.tipo_usuario);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            String token_usuarioUser = (String) valoresAdicionales.getString("token_usuario_admin");
            String nombre_usuarioUser = (String) valoresAdicionales.getString("nombre_usuario_admin");
            String email_usuarioUser = (String) valoresAdicionales.getString("email_usuario_admin");
            Integer id_tipo_usuarioUser = (Integer) valoresAdicionales.getInt("id_tipo_usuario_admin");

            name.setText(nombre_usuarioUser);
            email.setText(email_usuarioUser);

            if(id_tipo_usuarioUser.equals(1)){
                position.setText("Administrador");
            }if(id_tipo_usuarioUser.equals(2)){
                position.setText("Empleado");
            }

        }
    }

    public void openEditUsuario(View View){
        Bundle valoresAdicionales = getIntent().getExtras();
        if (valoresAdicionales != null){
            Intent intent = new Intent(this, UpdatePerfil.class);

            String token_usuarioUser = (String) valoresAdicionales.getString("token_usuario_admin");
            String nombre_usuarioUser = (String) valoresAdicionales.getString("nombre_usuario_admin");
            String email_usuarioUser = (String) valoresAdicionales.getString("email_usuario_admin");
            Integer id_tipo_usuarioUser = (Integer) valoresAdicionales.getInt("id_tipo_usuario_admin");
            Integer id_usuarioUser = (Integer) valoresAdicionales.getInt("id_usuario_admin");

            intent.putExtra("id_usuario_user", id_usuarioUser);
            intent.putExtra("token_usuario_user", token_usuarioUser);
            intent.putExtra("nombre_usuario_user", nombre_usuarioUser);
            intent.putExtra("email_usuario_user", email_usuarioUser);
            intent.putExtra("id_tipo_usuario_user", id_tipo_usuarioUser);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }

    }

    public void openAdminSpecialty(){
        startActivity(new Intent(this, Admin.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}