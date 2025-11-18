package com.proj8.idt_fa.Model.DeptSpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeptSpin {

    @SerializedName("data")
    @Expose
    private List<DataDept> data = null;

    public List<DataDept> getData() {
        return data;
    }

    public void setData(List<DataDept> data) {
        this.data = data;
    }
}
