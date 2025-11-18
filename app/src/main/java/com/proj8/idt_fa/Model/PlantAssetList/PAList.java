package com.proj8.idt_fa.Model.PlantAssetList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PAList
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ArrayList<DataPAList> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DataPAList> getData() {
        return data;
    }

    public void setData(ArrayList<DataPAList> data) {
        this.data = data;
    }

}
