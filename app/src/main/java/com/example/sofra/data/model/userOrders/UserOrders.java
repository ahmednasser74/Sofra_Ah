
package com.example.sofra.data.model.userOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserOrders {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private UserOrdersPagination data;

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

    public UserOrdersPagination getData() {
        return data;
    }

    public void setData(UserOrdersPagination data) {
        this.data = data;
    }

}
