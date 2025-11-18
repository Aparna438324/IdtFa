package com.proj8.idt_fa.Model.PlantSpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPlant {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("plant_name")
    @Expose
    private String plantName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

}
