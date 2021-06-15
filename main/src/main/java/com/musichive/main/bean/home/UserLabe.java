package com.musichive.main.bean.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLabe {

    /**
     * id : 1
     * title : 曲作者
     * status : 1
     * create_time : 1574771480941
     * update_time : 1574771480941
     * deleted : 0
     */
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("create_time")
    @Expose
    private String create_time;
    @SerializedName("update_time")
    @Expose
    private long update_time;
    @SerializedName("deleted")
    @Expose
    private int deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
