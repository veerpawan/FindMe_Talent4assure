package pawan.t4a.com.onlinetracing.Models;

/**
 * Created by Deepak Upadhyay on 19-Jul-16.
 */
public class SimCardInfoBean {
    private String simID;
    private String simCompanyName;
    private String phoneEMINo;

    public SimCardInfoBean() {

    }

    public SimCardInfoBean(String simID, String simCompanyName, String phoneEMINo) {
        this.simID = simID;
        this.simCompanyName = simCompanyName;
        this.phoneEMINo = phoneEMINo;
    }

    public String getSimID() {
        return simID;
    }

    public String setSimID(String simID) {
        this.simID = simID;
        return simID;
    }

    public String getSimCompanyName() {
        return simCompanyName;
    }

    public String setSimCompanyName(String simCompanyName) {
        this.simCompanyName = simCompanyName;
        return simCompanyName;
    }

    public String getPhoneEMINo() {
        return phoneEMINo;
    }

    public String setPhoneEMINo(String phoneEMINo) {
        this.phoneEMINo = phoneEMINo;
        return phoneEMINo;
    }
}
