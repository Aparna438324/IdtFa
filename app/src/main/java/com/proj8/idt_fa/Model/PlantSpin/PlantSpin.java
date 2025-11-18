package com.proj8.idt_fa.Model.PlantSpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlantSpin {

    @SerializedName("data")
    @Expose
    private List<DataPlant> data = null;

    public List<DataPlant> getData() {
        return data;
    }

    public void setData(List<DataPlant> data) {
        this.data = data;
    }
}
