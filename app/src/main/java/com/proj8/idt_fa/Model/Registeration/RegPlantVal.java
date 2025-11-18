package com.proj8.idt_fa.Model.Registeration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegPlantVal {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<RegPlantValDetail> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RegPlantValDetail> getData() {
        return data;
    }

    public void setData(List<RegPlantValDetail> data) {
        this.data = data;
    }

}
