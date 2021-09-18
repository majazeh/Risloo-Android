package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class Paymont {

    // Instance
    private static Paymont instance;

    // Models
    private TypeModel typeModel = null;

    // Vars
    private HashMap hashmap = null;
    private int destination = 0;

    /*
    ---------- Intialize ----------
    */

    private Paymont() {
        // TODO : Place Code If Needed
    }

    public static synchronized Paymont getInstance() {
        if (instance == null) {
            instance = new Paymont();
        }

        return instance;
    }

    /*
    ---------- Setters ----------
    */

    public void insertPayment(TypeModel typeModel, HashMap hashmap, int destination) {
        setTypeModel(typeModel);
        setHashmap(hashmap);
        setDestination(destination);
    }

    public void clearPayment() {
        this.typeModel = null;
        this.hashmap = null;
        this.destination = 0;
    }

    /*
    ---------- Setters ----------
    */

    private void setTypeModel(TypeModel typeModel) {
        this.typeModel = typeModel;
    }

    private void setHashmap(HashMap hashmap) {
        this.hashmap = hashmap;
    }

    private void setDestination(int destination) {
        this.destination = destination;
    }

    /*
    ---------- Getters ----------
    */

    public TypeModel getTypeModel() {
        return typeModel;
    }

    public HashMap getHashmap() {
        return hashmap;
    }

    public int getDestination() {
        return destination;
    }

}