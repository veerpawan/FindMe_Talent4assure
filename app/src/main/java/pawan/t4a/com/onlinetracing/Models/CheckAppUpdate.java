package pawan.t4a.com.onlinetracing.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pawan on 2/6/2017.
 */

public class CheckAppUpdate {

    @SerializedName("app_id")
    private String app_id;

    @SerializedName("app_name")
    private String app_name;

    @SerializedName("package_name")
    private String package_name;

    @SerializedName("version_code")
    private String version_code;

    @SerializedName("version_name")
    private String version_name;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
}
