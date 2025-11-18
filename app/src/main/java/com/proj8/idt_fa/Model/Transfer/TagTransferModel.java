package com.proj8.idt_fa.Model.Transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagTransferModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<TagTransferModelData> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TagTransferModelData> getData() {
        return data;
    }

    public void setData(List<TagTransferModelData> data) {
        this.data = data;
    }

}
