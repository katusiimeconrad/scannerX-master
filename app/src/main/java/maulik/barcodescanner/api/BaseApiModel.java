package maulik.barcodescanner.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseApiModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("apiToken")
    @Expose
    private String apiToken;

    @SerializedName("deviceID")
    @Expose
    private String deviceID;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
