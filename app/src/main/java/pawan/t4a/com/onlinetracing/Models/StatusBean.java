package pawan.t4a.com.onlinetracing.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pawan on 12/27/2016.
 */

public class StatusBean {
    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
