package com.amtodev.hospitalReservations.services.helpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class tokenResponse implements Serializable {


    @SerializedName("results")
    @Expose
    private List<results> results;

    public List<results> getResults() {
        return results;
    }

    public void setResults(List<results> results) {
        this.results = results;
    }

    public tokenResponse(List<results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "tokenResponse{" +
                "results=" + results +
                '}';
    }

}

