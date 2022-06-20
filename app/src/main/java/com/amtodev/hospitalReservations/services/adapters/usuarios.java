package com.amtodev.hospitalReservations.services.adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.incidents.UpdateIncidents;
import com.amtodev.hospitalReservations.admin.usuarios.UpdateUsuarios;
import com.amtodev.hospitalReservations.services.helpers.DataIncidents;
import com.amtodev.hospitalReservations.services.helpers.DataUsuarios;

import java.util.ArrayList;

public class usuarios extends RecyclerView.Adapter<usuarios.DataViewHolder> {

    private CardView cardView;
    Context contexto;
    LayoutInflater inflaterAdaptador;

    ArrayList<DataUsuarios> listUsuario = new ArrayList<>() ;
    private usuarios.OnUsuariosListener mOnUsuariosListener;

    public usuarios(Context contexto, ArrayList<DataUsuarios> listUsuario, usuarios.OnUsuariosListener mOnUsuariosListener){
        this.contexto = contexto;
        this.listUsuario = listUsuario;
        this.mOnUsuariosListener = mOnUsuariosListener;
    }

    @NonNull
    @Override
    public usuarios.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_usuarios, parent, false);
        return new usuarios.DataViewHolder(itemView, mOnUsuariosListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.viewBind(listUsuario.get(position), position);
    }


    @Override
    public int getItemCount() {
        return listUsuario.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView UserName,  UserId, UserCorreo, UserType, UserEstado, TaskOption;
        usuarios.OnUsuariosListener mOnUsuariosListener;

        public DataViewHolder(@NonNull View itemView, usuarios.OnUsuariosListener mOnUsuariosListener) {
            super(itemView);
            UserName = itemView.findViewById(R.id.UserName);
            UserId = itemView.findViewById(R.id.UserId);
            UserCorreo = itemView.findViewById(R.id.UserCorreo);
            UserType = itemView.findViewById(R.id.UserType);
            UserEstado = itemView.findViewById(R.id.UserEstado);
            TaskOption = itemView.findViewById(R.id.txtOption2);
            itemView.setOnClickListener(this);
            this.mOnUsuariosListener = mOnUsuariosListener;
        }
        @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
        public void viewBind(DataUsuarios dataUsuario, int position) {

            String id_estado_incidente = "";

            if(dataUsuario.getId_tipo_usuario().equals(1)){
                id_estado_incidente = "Administrador";
            }if(dataUsuario.getId_tipo_usuario().equals(2)){
                id_estado_incidente = "Empleado";
            }

            UserName.setText(dataUsuario.getNombre_usuario());
            UserId.setText("Usuario ID: " + dataUsuario.getId_usuario());
            UserCorreo.setText("Correo: " + dataUsuario.getEmail_usuario());
            UserType.setText("Tipo de Incidente: " + id_estado_incidente);
            UserEstado.setText("Estado: Activo");

            TaskOption.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(contexto, TaskOption);
                popupMenu.inflate(R.menu.menu_item);
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()){
                        case R.id.menu_edit:
                            Intent intent = new Intent(contexto, UpdateUsuarios.class);
                            intent.putExtra("edit-user", (Parcelable) dataUsuario);
                            contexto.startActivity(intent);
                            break;
                    }
                    return  false;
                });
                popupMenu.show();
            });

        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onCLick: " + getAdapterPosition());
            mOnUsuariosListener.onUsuariosClick(getAdapterPosition());
        }
    }


    public interface OnUsuariosListener{
        void onUsuariosClick(int position);
    }
}
