package com.proj8.idt_fa.Model.RegisterSend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JORegisteration {
    @SerializedName("updatedby")
    @Expose
    private String updatedby;
    @SerializedName("datas")
    @Expose
    private List<JORegDetail> datas = null;

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public List<JORegDetail> getDatas() {
        return datas;
    }

    public void setDatas(List<JORegDetail> datas) {
        this.datas = datas;
    }
}
