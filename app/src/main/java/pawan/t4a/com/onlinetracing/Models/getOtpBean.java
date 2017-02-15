package pawan.t4a.com.onlinetracing.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pawan on 2/13/2017.
 */

public class getOtpBean {


    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;
    @SerializedName("user_otp")
    private String user_otp;

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

    public String getUser_otp() {
        return user_otp;
    }

    public void setUser_otp(String user_otp) {
        this.user_otp = user_otp;
    }
}
