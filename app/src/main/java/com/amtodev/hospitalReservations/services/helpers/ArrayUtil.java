package com.amtodev.hospitalReservations.services.helpers;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayUtil
{
    public static ArrayList<DataIncidents> convert(JSONArray jArr)
    {
        ArrayList<DataIncidents> list = new ArrayList<DataIncidents>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                list.add((DataIncidents) jArr.get(i));
            }
        } catch (JSONException e) {}

        return list;
    }

    public static JSONArray convert(Collection<Object> list)
    {
        return new JSONArray(list);
    }

}