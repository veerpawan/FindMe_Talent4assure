package pawan.t4a.com.onlinetracing.Interface;

import java.util.List;

import pawan.t4a.com.onlinetracing.Models.CheckAppUpdate;
import pawan.t4a.com.onlinetracing.Models.CheckUserBean;
import pawan.t4a.com.onlinetracing.Models.CloseStatus;
import pawan.t4a.com.onlinetracing.Models.CurrentUserLocationBean;
import pawan.t4a.com.onlinetracing.Models.StatusBean;
import pawan.t4a.com.onlinetracing.Models.UserDetailBean;
import pawan.t4a.com.onlinetracing.Models.UserStatus;
import pawan.t4a.com.onlinetracing.Models.UserSuccessBean;
import pawan.t4a.com.onlinetracing.Models.getOtpBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by pawan on 1/23/2017.
 */

public interface RequestInterface {


    @FormUrlEncoded
    @POST("GetUserAddress")
    Call<List<StatusBean>> location_of_user(@Field("address") String address,
                                            @Field("user_id") String userid,
                                            @Field("latitude") Double latitude,
                                            @Field("longitude") Double longitude
    );


    @FormUrlEncoded
    @POST("RegisterUser")
    Call<List<UserSuccessBean>> reg_user(@Field("user_fname") String user_fname,
                                         @Field("user_mname") String user_mname,
                                         @Field("user_lname") String user_lname,
                                         @Field("user_email") String useremail,
                                         @Field("user_phone") String userphone,
                                         @Field("user_password") String userpassword,
                                         @Field("user_dob") String userdob,
                                         @Field("user_simserial") String simserial,
                                         @Field("user_simname") String simname,
                                         @Field("user_imeinumber") String imeinumber
    );

    @FormUrlEncoded
    @POST("LoginUser")
    Call<List<StatusBean>> loginuser(@Field("user_name") String username,
                                     @Field("user_password") String password
    );


    @GET("GetAllUserID")
    Call<List<UserDetailBean>> get_userdetails();

    @FormUrlEncoded
    @POST("GetLocationByID")
    Call<List<CurrentUserLocationBean>> send_userdetails(@Field("user_id") String s_selected_id,
                                                         @Field("from_date") String frm_date,
                                                         @Field("to_date") String to_date);

    @GET("GetAllUserID")
    Call<List<CheckUserBean>> checkuser();


    @GET("CheckAppUpdate")
    Call<List<CheckAppUpdate>> checkappversion();


    @FormUrlEncoded
    @POST("UserStatus")
    Call<List<UserStatus>> sendImei(@Field("sim_num") String serialnumber,
                                    @Field("imei_num") String imeinumber);

    @FormUrlEncoded
    @POST("GetOtp")
    Call<List<getOtpBean>> get_otp(@Field("user_phone") String str_mobile);


    @FormUrlEncoded
    @POST("SendCloseStatus")
    Call<List<CloseStatus>> closestatus(@Field("user_id") String userId, @Field("user_address") String locationaddress);
}
