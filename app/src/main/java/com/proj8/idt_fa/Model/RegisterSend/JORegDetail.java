package com.proj8.idt_fa.Model.RegisterSend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JORegDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tagid")
    @Expose
    private String tagid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }
}
