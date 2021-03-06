
package com.example.sofra.data.model.restaurantLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRestaurant {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private AuthRestaurantData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AuthRestaurantData getData() {
        return data;
    }

    public void setData(AuthRestaurantData data) {
        this.data = data;
    }

}
