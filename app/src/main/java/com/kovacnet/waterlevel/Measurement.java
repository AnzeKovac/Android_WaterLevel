package com.kovacnet.waterlevel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Table(name = "Measurements")
public class Measurement extends Model implements Serializable  {
    @Column(name = "waterLevel")
    @SerializedName("waterLevel")
    @Expose
    private String waterLevel;
    @Column(name = "battery")
    @SerializedName("battery")
    @Expose
    private String battery;
    @Column(name = "datetime")
    @SerializedName("datetime")
    @Expose
    private String datetime;

    /**
     *
     * @return
     * The waterLevel
     */
    public String getWaterLevel() {
        return waterLevel;
    }

    /**
     *
     * @param waterLevel
     * The waterLevel
     */
    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    /**
     *
     * @return
     * The battery
     */
    public String getBattery() {
        return battery;
    }

    /**
     *
     * @param battery
     * The battery
     */
    public void setBattery(String battery) {
        this.battery = battery;
    }

    /**
     *
     * @return
     * The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     *
     * @param datetime
     * The datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Measurement [waterlevel=" + waterLevel + ", battery=" + battery + ", datetime=" + datetime + "]";
    }


}