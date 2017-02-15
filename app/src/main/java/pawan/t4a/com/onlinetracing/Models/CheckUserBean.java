package pawan.t4a.com.onlinetracing.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pawan on 2/3/2017.
 */

public class CheckUserBean  {


    @SerializedName("user_role")
    private String user_role;

    @SerializedName("user_dob")
    private String user_dob;

    @SerializedName("user_email")
    private String user_email;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_mobile")
    private String user_mobile;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_imei")
    private String user_imei;


    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_imei() {
        return user_imei;
    }

    public void setUser_imei(String user_imei) {
        this.user_imei = user_imei;
    }
}
