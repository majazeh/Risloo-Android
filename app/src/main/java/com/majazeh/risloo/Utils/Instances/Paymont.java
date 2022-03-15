package com.majazeh.risloo.utils.instances;

import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class Paymont {

    // Instance
    private static Paymont instance;

    // Models
    private TypeModel typeModel = null;
    private PaymentModel paymentModel = null;

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
    ---------- Func's ----------
    */

    public void insertPayment(TypeModel typeModel, PaymentModel paymentModel, HashMap hashmap, int destination) {
        setTypeModel(typeModel);
        setPaymentModel(paymentModel);
        setHashmap(hashmap);
        setDestination(destination);
    }

    public void clearPayment() {
        this.typeModel = null;
        this.paymentModel = null;
        this.hashmap = null;
        this.destination = 0;
    }

    /*
    ---------- Setter's ----------
    */

    private void setTypeModel(TypeModel typeModel) {
        this.typeModel = typeModel;
    }

    private void setPaymentModel(PaymentModel paymentModel) {
        this.paymentModel = paymentModel;
    }

    private void setHashmap(HashMap hashmap) {
        this.hashmap = hashmap;
    }

    private void setDestination(int destination) {
        this.destination = destination;
    }

    /*
    ---------- Getter's ----------
    */

    public TypeModel getTypeModel() {
        return typeModel;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public HashMap getHashmap() {
        return hashmap;
    }

    public int getDestination() {
        return destination;
    }

}