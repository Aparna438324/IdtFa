package com.proj8.idt_fa.Model.ScanPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanPostData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tag_id")
    @Expose
    private String tagId;
    @SerializedName("asset_primary_code")
    @Expose
    private String assetPrimaryCode;
    @SerializedName("scan_status")
    @Expose
    private String scanStatus;

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

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public ScanPostData(String id, String tagId, String scanStatus) {
        this.id = id;
        this.tagId = tagId;
        this.scanStatus = scanStatus;
    }

}
