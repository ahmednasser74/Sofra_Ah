
package com.example.sofra.data.model.restaurantLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRestaurantData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private Restaurant user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Restaurant getUser() {
        return user;
    }

    public void setUser(Restaurant user) {
        this.user = user;
    }

}
