package com.amtodev.hospitalReservations.services.helpers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DataIncidents implements Parcelable, Serializable {

    private Integer id_incidente;
    private Integer id_tipo_incidente;
    private String descripcion_incidente;
    private String fecha_incidente;
    private Integer id_usuario_incidente;
    private String imagen_incidente;
    private Integer id_estado_incidente;
    private String nombre_usuario;
    private String token_usuario;

    public DataIncidents(){

    }

    protected DataIncidents(Parcel in) {
        if (in.readByte() == 0) {
            id_incidente = null;
        } else {
            id_incidente = in.readInt();
        }
        if (in.readByte() == 0) {
            id_tipo_incidente = null;
        } else {
            id_tipo_incidente = in.readInt();
        }
        descripcion_incidente = in.readString();
        fecha_incidente = in.readString();
        if (in.readByte() == 0) {
            id_usuario_incidente = null;
        } else {
            id_usuario_incidente = in.readInt();
        }
        imagen_incidente = in.readString();
        if (in.readByte() == 0) {
            id_estado_incidente = null;
        } else {
            id_estado_incidente = in.readInt();
        }
        nombre_usuario = in.readString();
        token_usuario = in.readString();
    }

    public static final Creator<DataIncidents> CREATOR = new Creator<DataIncidents>() {
        @Override
        public DataIncidents createFromParcel(Parcel in) {
            return new DataIncidents(in);
        }

        @Override
        public DataIncidents[] newArray(int size) {
            return new DataIncidents[size];
        }
    };

    public Integer getId_incidente() {
        return id_incidente;
    }

    public void setId_incidente(Integer id_incidente) {
        this.id_incidente = id_incidente;
    }

    public Integer getId_tipo_incidente() {
        return id_tipo_incidente;
    }

    public void setId_tipo_incidente(Integer id_tipo_incidente) {
        this.id_tipo_incidente = id_tipo_incidente;
    }

    public String getDescripcion_incidente() {
        return descripcion_incidente;
    }

    public void setDescripcion_incidente(String descripcion_incidente) {
        this.descripcion_incidente = descripcion_incidente;
    }

    public String getFecha_incidente() {
        return fecha_incidente;
    }

    public void setFecha_incidente(String fecha_incidente) {
        this.fecha_incidente = fecha_incidente;
    }

    public Integer getId_usuario_incidente() {
        return id_usuario_incidente;
    }

    public void setId_usuario_incidente(Integer id_usuario_incidente) {
        this.id_usuario_incidente = id_usuario_incidente;
    }

    public String getImagen_incidente() {
        return imagen_incidente;
    }

    public void setImagen_incidente(String imagen_incidente) {
        this.imagen_incidente = imagen_incidente;
    }

    public Integer getId_estado_incidente() {
        return id_estado_incidente;
    }

    public void setId_estado_incidente(Integer id_estado_incidente) {
        this.id_estado_incidente = id_estado_incidente;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getToken_usuario() {
        return token_usuario;
    }

    public void setToken_usuario(String token_usuario) {
        this.token_usuario = token_usuario;
    }

    @Override
    public String toString() {
        return "DataIncidents{" +
                "id_incidente=" + id_incidente +
                ", id_tipo_incidente=" + id_tipo_incidente +
                ", descripcion_incidente='" + descripcion_incidente + '\'' +
                ", fecha_incidente='" + fecha_incidente + '\'' +
                ", id_usuario_incidente=" + id_usuario_incidente +
                ", imagen_incidente='" + imagen_incidente + '\'' +
                ", id_estado_incidente=" + id_estado_incidente +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", token_usuario='" + token_usuario + '\'' +
                '}';
    }

    public DataIncidents(Integer id_incidente, Integer id_tipo_incidente, String descripcion_incidente, String fecha_incidente, Integer id_usuario_incidente, String imagen_incidente, Integer id_estado_incidente, String nombre_usuario, String token_usuario) {
        this.id_incidente = id_incidente;
        this.id_tipo_incidente = id_tipo_incidente;
        this.descripcion_incidente = descripcion_incidente;
        this.fecha_incidente = fecha_incidente;
        this.id_usuario_incidente = id_usuario_incidente;
        this.imagen_incidente = imagen_incidente;
        this.id_estado_incidente = id_estado_incidente;
        this.nombre_usuario = nombre_usuario;
        this.token_usuario = token_usuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_incidente == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_incidente);
        }
        if (id_tipo_incidente == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_tipo_incidente);
        }
        dest.writeString(descripcion_incidente);
        dest.writeString(fecha_incidente);
        if (id_usuario_incidente == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_usuario_incidente);
        }
        dest.writeString(imagen_incidente);
        if (id_estado_incidente == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_estado_incidente);
        }
        dest.writeString(nombre_usuario);
        dest.writeString(token_usuario);
    }
}
