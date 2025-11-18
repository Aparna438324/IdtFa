package com.proj8.idt_fa.Model.Transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinalTransfer {

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
