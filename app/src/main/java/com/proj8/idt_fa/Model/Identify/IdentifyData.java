package com.proj8.idt_fa.Model.Identify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdentifyData {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DataIdentifyList> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataIdentifyList> getData() {
        return data;
    }

    public void setData(List<DataIdentifyList> data) {
        this.data = data;
    }

}
