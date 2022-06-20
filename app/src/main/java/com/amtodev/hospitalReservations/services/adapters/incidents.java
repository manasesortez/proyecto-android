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
import com.amtodev.hospitalReservations.services.helpers.DataIncidents;

import org.json.JSONArray;

import java.util.ArrayList;

public class incidents extends RecyclerView.Adapter<incidents.DataViewHolder>{

    private CardView cardView;
    Context context;
    LayoutInflater inflaterAdaptador;

    ArrayList<DataIncidents> listIncidents = new ArrayList<>() ;
    private incidents.OnIncidentsListener mOnIncidentesListener;

    public incidents(Context context, ArrayList<DataIncidents> listIncidents, OnIncidentsListener mOnIncidentesListener){
        this.context = context;
        this.listIncidents = listIncidents;
        this.mOnIncidentesListener = mOnIncidentesListener;
    }

    @NonNull
    @Override
    public incidents.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerspecialty, parent, false);
        return new incidents.DataViewHolder(itemView, mOnIncidentesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.viewBind(listIncidents.get(position), position);
    }


    @Override
    public int getItemCount() {
        return listIncidents.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView IncidentName,  IncidentsId, IncidentType, DateIncidents, IncidentUsuario, IncidentEstado, TaskOption;
        incidents.OnIncidentsListener mOnReservasListener;

        public DataViewHolder(@NonNull View itemView, OnIncidentsListener mOnIncidentesListener) {
            super(itemView);
            IncidentName = itemView.findViewById(R.id.IncidentName);
            IncidentsId = itemView.findViewById(R.id.IncidentsId);
            IncidentType = itemView.findViewById(R.id.IncidentType);
            DateIncidents = itemView.findViewById(R.id.DateIncidents);
            IncidentUsuario = itemView.findViewById(R.id.IncidentUsuario);
            IncidentEstado = itemView.findViewById(R.id.IncidentEstado);
            TaskOption = itemView.findViewById(R.id.txtOption);

            itemView.setOnClickListener(this);
            this.mOnReservasListener = mOnIncidentesListener;
        }
        @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
        public void viewBind(DataIncidents dataReservas, int position) {

            String id_estado_incidente = "";

            if(dataReservas.getId_estado_incidente().equals(1)){
                id_estado_incidente = "Incidente Activo";
            }if(dataReservas.getId_estado_incidente().equals(2)){
                id_estado_incidente = "Incidente Resuelto";
            }

            String id_tipo_incidente = "";

            if(dataReservas.getId_estado_incidente().equals(1)){
                id_tipo_incidente = "Ataques cibernéticos";
            }if(dataReservas.getId_estado_incidente().equals(2)){
                id_tipo_incidente = "Código malicioso";
            }if(dataReservas.getId_estado_incidente().equals(3)){
                id_tipo_incidente = "Denegación de Servicio";
            }if(dataReservas.getId_estado_incidente().equals(4)){
                id_tipo_incidente = "Denegación de Servicio Distribuida";
            }if(dataReservas.getId_estado_incidente().equals(5)){
                id_tipo_incidente = "Acceso no autorizado";
            }if(dataReservas.getId_estado_incidente().equals(6)){
                id_tipo_incidente = "Pérdida de datos";
            }if(dataReservas.getId_estado_incidente().equals(7)){
                id_tipo_incidente = "Daños físicos";
            }if(dataReservas.getId_estado_incidente().equals(8)){
                id_tipo_incidente = "Abuso de privilegios";
            }

            IncidentName.setText(dataReservas.getDescripcion_incidente());
            IncidentsId.setText("Incidente ID: " + dataReservas.getId_incidente());
            IncidentType.setText("Tipo de Incidente: " + id_tipo_incidente);
            DateIncidents.setText("Fecha de Ingreso: " + dataReservas.getFecha_incidente());
            IncidentUsuario.setText("Usuario: " + dataReservas.getNombre_usuario());
            IncidentEstado.setText("Estado: " + id_estado_incidente);

            TaskOption.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context, TaskOption);
                popupMenu.inflate(R.menu.menu_item);
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()){
                        case R.id.menu_edit:
                            Intent intent = new Intent(context, UpdateIncidents.class);
                            intent.putExtra("edit", (Parcelable) dataReservas);
                            context.startActivity(intent);
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
            mOnReservasListener.onIncidentsClick(getAdapterPosition());
        }
    }


    public interface OnIncidentsListener{
        void onIncidentsClick(int position);
    }

}
