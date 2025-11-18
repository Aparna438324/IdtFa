package com.proj8.idt_fa.Model.Identify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataIdentifyList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tag_id")
    @Expose
    private String tagId;
    @SerializedName("asset_primary_code")
    @Expose
    private String assetPrimaryCode;
    @SerializedName("asset_type")
    @Expose
    private String assetType;
    @SerializedName("finance_asset_id")
    @Expose
    private String financeAssetId;
    @SerializedName("sbu")
    @Expose
    private String sbu;
    @SerializedName("asset_class_1")
    @Expose
    private String assetClass1;
    @SerializedName("asset_class_2")
    @Expose
    private String assetClass2;
    @SerializedName("asset_sub_class")
    @Expose
    private String assetSubClass;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("plant")
    @Expose
    private String plant;
    @SerializedName("make_oem")
    @Expose
    private String makeOem;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("device_sl_no")
    @Expose
    private String deviceSlNo;
    @SerializedName("form_status")
    @Expose
    private String formStatus;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("it_asset_code")
    @Expose
    private String itAssetCode;
    @SerializedName("cost_center")
    @Expose
    private String costCenter;
    @SerializedName("po_no")
    @Expose
    private String poNo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("asset_status")
    @Expose
    private String assetStatus;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("capitalized_on")
    @Expose
    private String capitalizedOn;
    @SerializedName("delete_atatus")
    @Expose
    private String deleteAtatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("creation_ip")
    @Expose
    private String creationIp;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("updation_ip")
    @Expose
    private Object updationIp;
    @SerializedName("scan_status")
    @Expose
    private String scanStatus;
    @SerializedName("scan_by")
    @Expose
    private String scanBy;
    @SerializedName("scan_at")
    @Expose
    private String scanAt;
    @SerializedName("inventry_id")
    @Expose
    private String inventryId;
    @SerializedName("dept_name")
    @Expose
    private String deptName;
    @SerializedName("asset_type_name")
    @Expose
    private String assetTypeName;
    @SerializedName("plant_name")
    @Expose
    private String plantName;
    @SerializedName("loc_name")
    @Expose
    private String locName;

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

    public String getAssetPrimaryCode() {
        return assetPrimaryCode;
    }

    public void setAssetPrimaryCode(String assetPrimaryCode) {
        this.assetPrimaryCode = assetPrimaryCode;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getFinanceAssetId() {
        return financeAssetId;
    }

    public void setFinanceAssetId(String financeAssetId) {
        this.financeAssetId = financeAssetId;
    }

    public String getSbu() {
        return sbu;
    }

    public void setSbu(String sbu) {
        this.sbu = sbu;
    }

    public String getAssetClass1() {
        return assetClass1;
    }

    public void setAssetClass1(String assetClass1) {
        this.assetClass1 = assetClass1;
    }

    public String getAssetClass2() {
        return assetClass2;
    }

    public void setAssetClass2(String assetClass2) {
        this.assetClass2 = assetClass2;
    }

    public String getAssetSubClass() {
        return assetSubClass;
    }

    public void setAssetSubClass(String assetSubClass) {
        this.assetSubClass = assetSubClass;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getMakeOem() {
        return makeOem;
    }

    public void setMakeOem(String makeOem) {
        this.makeOem = makeOem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceSlNo() {
        return deviceSlNo;
    }

    public void setDeviceSlNo(String deviceSlNo) {
        this.deviceSlNo = deviceSlNo;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItAssetCode() {
        return itAssetCode;
    }

    public void setItAssetCode(String itAssetCode) {
        this.itAssetCode = itAssetCode;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCapitalizedOn() {
        return capitalizedOn;
    }

    public void setCapitalizedOn(String capitalizedOn) {
        this.capitalizedOn = capitalizedOn;
    }

    public String getDeleteAtatus() {
        return deleteAtatus;
    }

    public void setDeleteAtatus(String deleteAtatus) {
        this.deleteAtatus = deleteAtatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationIp() {
        return creationIp;
    }

    public void setCreationIp(String creationIp) {
        this.creationIp = creationIp;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdationIp() {
        return updationIp;
    }

    public void setUpdationIp(Object updationIp) {
        this.updationIp = updationIp;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public String getScanBy() {
        return scanBy;
    }

    public void setScanBy(String scanBy) {
        this.scanBy = scanBy;
    }

    public String getScanAt() {
        return scanAt;
    }

    public void setScanAt(String scanAt) {
        this.scanAt = scanAt;
    }

    public String getInventryId() {
        return inventryId;
    }

    public void setInventryId(String inventryId) {
        this.inventryId = inventryId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

}