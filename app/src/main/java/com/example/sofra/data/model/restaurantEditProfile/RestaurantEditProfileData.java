
package com.example.sofra.data.model.restaurantEditProfile;

import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantEditProfileData {

    @SerializedName("user")
    @Expose
    private Restaurant user;

    public Restaurant getUser() {
        return user;
    }

    public void setUser(Restaurant user) {
        this.user = user;
    }
}
