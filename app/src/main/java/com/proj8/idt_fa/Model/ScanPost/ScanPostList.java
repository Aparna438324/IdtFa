package com.proj8.idt_fa.Model.ScanPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScanPostList {
    @SerializedName("scan_data")
    @Expose
    private List<ScanPostData> scanData = null;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public List<ScanPostData> getScanData() {
        return scanData;
    }

    public void setScanData(List<ScanPostData> scanData) {
        this.scanData = scanData;
    }

    @SerializedName("scan_date_time")
    @Expose
    private String scanDateTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScanDateTime() {
        return scanDateTime;
    }

    public void setScanDateTime(String scanDateTime) {
        this.scanDateTime = scanDateTime;
    }

}
