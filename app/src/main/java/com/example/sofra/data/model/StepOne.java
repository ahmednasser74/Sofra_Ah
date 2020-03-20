package com.example.sofra.data.model;

import okhttp3.RequestBody;

public class StepOne {

    private RequestBody name;
    private RequestBody email;
    private RequestBody password;
    private RequestBody passwordConfirmation;
    private RequestBody deliveryCost;
    private RequestBody minimumCharger;
    private RequestBody deliveryTime;
    private RequestBody regionId;

    public StepOne(RequestBody name, RequestBody email, RequestBody password, RequestBody passwordConfirmation, RequestBody deliveryCost, RequestBody minimumCharger, RequestBody deliveryTime, RequestBody regionId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.deliveryCost = deliveryCost;
        this.minimumCharger = minimumCharger;
        this.deliveryTime = deliveryTime;
        this.regionId = regionId;
    }

    public RequestBody getName() {
        return name;
    }

    public void setName(RequestBody name) {
        this.name = name;
    }

    public RequestBody getEmail() {
        return email;
    }

    public void setEmail(RequestBody email) {
        this.email = email;
    }

    public RequestBody getPassword() {
        return password;
    }

    public void setPassword(RequestBody password) {
        this.password = password;
    }

    public RequestBody getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(RequestBody passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public RequestBody getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(RequestBody deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public RequestBody getMinimumCharger() {
        return minimumCharger;
    }

    public void setMinimumCharger(RequestBody minimumCharger) {
        this.minimumCharger = minimumCharger;
    }

    public RequestBody getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(RequestBody deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public RequestBody getRegionId() {
        return regionId;
    }

    public void setRegionId(RequestBody regionId) {
        this.regionId = regionId;
    }
}
