package com.proj8.idt_fa.Model.Transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagTransferModelData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tag_id")
    @Expose
    private String tagId;
    @SerializedName("finance_asset_id")
    @Expose
    private String financeAssetId;
    @SerializedName("it_asset_code")
    @Expose
    private String itAssetCode;
    @SerializedName("asset_user")
    @Expose
    private String assetUser;
    @SerializedName("source_plant_id")
    @Expose
    private String sourcePlantId;
    @SerializedName("source_dept_id")
    @Expose
    private String sourceDeptId;
    @SerializedName("plant_name")
    @Expose
    private String plantName;
    @SerializedName("dep_name")
    @Expose
    private String depName;
    @SerializedName("status_name")
    @Expose
    private String statusName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getFinanceAssetId() {
        return financeAssetId;
    }

    public void setFinanceAssetId(String financeAssetId) {
        this.financeAssetId = financeAssetId;
    }

    public String getItAssetCode() {
        return itAssetCode;
    }

    public void setItAssetCode(String itAssetCode) {
        this.itAssetCode = itAssetCode;
    }

    public String getAssetUser() {
        return assetUser;
    }

    public void setAssetUser(String assetUser) {
        this.assetUser = assetUser;
    }

    public String getSourcePlantId() {
        return sourcePlantId;
    }

    public void setSourcePlantId(String sourcePlantId) {
        this.sourcePlantId = sourcePlantId;
    }

    public String getSourceDeptId() {
        return sourceDeptId;
    }

    public void setSourceDeptId(String sourceDeptId) {
        this.sourceDeptId = sourceDeptId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
