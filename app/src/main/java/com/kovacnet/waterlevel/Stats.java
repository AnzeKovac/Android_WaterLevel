package com.kovacnet.waterlevel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("maxWT")
    @Expose
    private String maxWT;
    @SerializedName("minWT")
    @Expose
    private String minWT;
    @SerializedName("maxBT")
    @Expose
    private String maxBT;
    @SerializedName("minBT")
    @Expose
    private String minBT;
    @SerializedName("maxW")
    @Expose
    private String maxW;
    @SerializedName("minW")
    @Expose
    private String minW;
    @SerializedName("maxB")
    @Expose
    private String maxB;
    @SerializedName("minB")
    @Expose
    private String minB;

    /**
     * @return The maxWT
     */
    public String getMaxWT() {
        return maxWT;
    }

    /**
     * @param maxWT The maxWT
     */
    public void setMaxWT(String maxWT) {
        this.maxWT = maxWT;
    }

    /**
     * @return The minWT
     */
    public String getMinWT() {
        return minWT;
    }

    /**
     * @param minWT The minWT
     */
    public void setMinWT(String minWT) {
        this.minWT = minWT;
    }

    /**
     * @return The maxBT
     */
    public String getMaxBT() {
        return maxBT;
    }

    /**
     * @param maxBT The maxBT
     */
    public void setMaxBT(String maxBT) {
        this.maxBT = maxBT;
    }

    /**
     * @return The minBT
     */
    public String getMinBT() {
        return minBT;
    }

    /**
     * @param minBT The minBT
     */
    public void setMinBT(String minBT) {
        this.minBT = minBT;
    }

    /**
     * @return The maxW
     */
    public String getMaxW() {
        return maxW;
    }

    /**
     * @param maxW The maxW
     */
    public void setMaxW(String maxW) {
        this.maxW = maxW;
    }

    /**
     * @return The minW
     */
    public String getMinW() {
        return minW;
    }

    /**
     * @param minW The minW
     */
    public void setMinW(String minW) {
        this.minW = minW;
    }

    /**
     * @return The maxB
     */
    public String getMaxB() {
        return maxB;
    }

    /**
     * @param maxB The maxB
     */
    public void setMaxB(String maxB) {
        this.maxB = maxB;
    }

    /**
     * @return The minB
     */
    public String getMinB() {
        return minB;
    }
}