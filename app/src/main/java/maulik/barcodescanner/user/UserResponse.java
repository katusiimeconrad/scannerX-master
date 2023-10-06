package maulik.barcodescanner.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserResponse {

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("apiToken")
    @Expose
    private String apiToken;

    @SerializedName("deviceID")
    @Expose
    private String deviceID;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
}

