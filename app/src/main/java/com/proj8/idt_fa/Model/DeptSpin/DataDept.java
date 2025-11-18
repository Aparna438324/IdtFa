package com.proj8.idt_fa.Model.DeptSpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataDept {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dep_name")
    @Expose
    private String depName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
