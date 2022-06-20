package com.amtodev.hospitalReservations.services.helpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class results implements Serializable {

    @Override
    public String toString() {
        return "results{" +
                "nombre_usuario='" + nombre_usuario + '\'' +
                ", email_usuario='" + email_usuario + '\'' +
                ", image_usuario='" + image_usuario + '\'' +
                ", id_tipo_usuario=" + id_tipo_usuario +
                ", token_usuario='" + token_usuario + '\'' +
                ", token_exp_usuario=" + token_exp_usuario +
                '}';
    }

    @SerializedName("nombre_usuario")
    @Expose
    private String nombre_usuario;

    @SerializedName("email_usuario")
    @Expose
    private String email_usuario;

    @SerializedName("image_usuario")
    @Expose
    private String image_usuario;

    @SerializedName("id_tipo_usuario")
    @Expose
    private Integer id_tipo_usuario;

    @SerializedName("token_usuario")
    @Expose
    private String token_usuario;

    @SerializedName("token_exp_usuario")
    @Expose
    private Integer token_exp_usuario;

    public results(String nombre_usuario, String email_usuario, String image_usuario, Integer id_tipo_usuario, String token_usuario, Integer token_exp_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.email_usuario = email_usuario;
        this.image_usuario = image_usuario;
        this.id_tipo_usuario = id_tipo_usuario;
        this.token_usuario = token_usuario;
        this.token_exp_usuario = token_exp_usuario;
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

    public Integer getToken_exp_usuario() {
        return token_exp_usuario;
    }

    public void setToken_exp_usuario(Integer token_exp_usuario) {
        this.token_exp_usuario = token_exp_usuario;
    }


}
