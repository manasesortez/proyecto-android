package com.amtodev.hospitalReservations.user.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSpecialty implements Parcelable {

    private Integer especialidad_id;
    private String especialidad_nombre;

    public DataSpecialty(Integer especialidad_id, String especialidad_nombre, String hospital_name) {
        this.especialidad_id = especialidad_id;
        this.especialidad_nombre = especialidad_nombre;
    }

    protected DataSpecialty(Parcel in) {
        if (in.readByte() == 0) {
            especialidad_id = null;
        } else {
            especialidad_id = in.readInt();
        }
        especialidad_nombre = in.readString();
    }

    public static final Creator<DataSpecialty> CREATOR = new Creator<DataSpecialty>() {
        @Override
        public DataSpecialty createFromParcel(Parcel in) {
            return new DataSpecialty(in);
        }

        @Override
        public DataSpecialty[] newArray(int size) {
            return new DataSpecialty[size];
        }
    };

    public Integer getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(Integer especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    public String getEspecialidad_nombre() {
        return especialidad_nombre;
    }

    public void setEspecialidad_nombre(String especialidad_nombre) {
        this.especialidad_nombre = especialidad_nombre;
    }



    
    @Override
    public String toString() {
        return "DataSpecialty{" +
                "especialidad_id=" + especialidad_id +
                ", especialidad_nombre='" + especialidad_nombre + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (especialidad_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(especialidad_id);
        }
        parcel.writeString(especialidad_nombre);
    }
}
