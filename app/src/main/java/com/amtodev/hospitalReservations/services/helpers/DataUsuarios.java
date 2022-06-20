package com.amtodev.hospitalReservations.services.helpers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DataUsuarios implements Parcelable, Serializable {

    private Integer id_usuario;
    private String nombre_usuario;
    private String email_usuario;
    private String password_usuario;
    private String image_usuario;
    private Integer id_tipo_usuario;
    private String token_usuario;


    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getPassword_usuario() {
        return password_usuario;
    }

    public void setPassword_usuario(String password_usuario) {
        this.password_usuario = password_usuario;
    }

    public String getImage_usuario() {
        return image_usuario;
    }

    public void setImage_usuario(String image_usuario) {
        this.image_usuario = image_usuario;
    }

    public Integer getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(Integer id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public String getToken_usuario() {
        return token_usuario;
    }

    public void setToken_usuario(String token_usuario) {
        this.token_usuario = token_usuario;
    }


    public DataUsuarios(Integer id_usuario, String nombre_usuario, String email_usuario, String password_usuario, String image_usuario, Integer id_tipo_usuario, String token_usuario) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.email_usuario = email_usuario;
        this.password_usuario = password_usuario;
        this.image_usuario = image_usuario;
        this.id_tipo_usuario = id_tipo_usuario;
        this.token_usuario = token_usuario;
    }

    @Override
    public String toString() {
        return "DataUsuarios{" +
                "id_usuario=" + id_usuario +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", email_usuario='" + email_usuario + '\'' +
                ", password_usuario='" + password_usuario + '\'' +
                ", image_usuario='" + image_usuario + '\'' +
                ", id_tipo_usuario=" + id_tipo_usuario +
                ", token_usuario='" + token_usuario + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_usuario == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_usuario);
        }
        dest.writeString(nombre_usuario);
        dest.writeString(email_usuario);
        dest.writeString(password_usuario);
        dest.writeString(image_usuario);
        if (id_tipo_usuario == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_tipo_usuario);
        }
        dest.writeString(token_usuario);
    }

    protected DataUsuarios(Parcel in) {
        if (in.readByte() == 0) {
            id_usuario = null;
        } else {
            id_usuario = in.readInt();
        }
        nombre_usuario = in.readString();
        email_usuario = in.readString();
        password_usuario = in.readString();
        image_usuario = in.readString();
        if (in.readByte() == 0) {
            id_tipo_usuario = null;
        } else {
            id_tipo_usuario = in.readInt();
        }
        token_usuario = in.readString();
    }

    public static final Creator<DataUsuarios> CREATOR = new Creator<DataUsuarios>() {
        @Override
        public DataUsuarios createFromParcel(Parcel in) {
            return new DataUsuarios(in);
        }

        @Override
        public DataUsuarios[] newArray(int size) {
            return new DataUsuarios[size];
        }
    };
}
