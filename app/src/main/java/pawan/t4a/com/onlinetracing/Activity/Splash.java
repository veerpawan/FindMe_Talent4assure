package pawan.t4a.com.onlinetracing.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pawan.t4a.com.onlinetracing.Admin.AdminControl;
import pawan.t4a.com.onlinetracing.Interface.RequestInterface;
import pawan.t4a.com.onlinetracing.Models.CheckUserBean;
import pawan.t4a.com.onlinetracing.Models.SimCardInfoBean;
import pawan.t4a.com.onlinetracing.Models.UserStatus;
import pawan.t4a.com.onlinetracing.R;
import pawan.t4a.com.onlinetracing.constant.URL_Mapping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pawan on 1/30/2017.
 */

public class Splash extends AppCompatActivity {
    TextView tv_splash;
    String user_id, user_name;
    int user_role;
    SharedPreferences shared;
    List<CheckUserBean> checkUserBeen;
    List<UserStatus> userStatuses;
    int role;
    SimCardInfoBean simCardInfoBean = new SimCardInfoBean();
    String serialnumber, operatorname, imeinumber;
    String serialnumber_m, operatorname_m, imeinumber_m;
    int status;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        tv_splash = (TextView) findViewById(R.id.tv_splash);


        //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)) {
            loadIMEI();
        } else {


            ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_PHONE_STATE);
            ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.INTERNET);
            ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE);
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            serialnumber = simCardInfoBean.setSimID(tm.getSimSerialNumber());
            operatorname = simCardInfoBean.setSimCompanyName(tm.getSimOperatorName());
            imeinumber = simCardInfoBean.setPhoneEMINo(tm.getDeviceId());
            Log.e("noMarsh-getSimID:", serialnumber);
            Log.e("noMarsh-getSimCompany:", operatorname);
            Log.e("noMarsh-getPhoneEMINo:", imeinumber);
        }
        if (isNetworkAvailable()) {

            checkimei();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }
      /*  shared = getSharedPreferences("MyPrefs", 0);
        user_id = shared.getString("userID", null);
        user_name = shared.getString("username", null);*/
        //getappversion();
        //checkuser();*/

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/ts_normal_bold.ttf");
        tv_splash.setTypeface(type);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


               /* if (user_id == null) {
                    Intent mainIntent = new Intent(Splash.this, RegisterActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();

                } else*/
                if (status == 1) {
                    Intent mainIntent = new Intent(Splash.this, AdminControl.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();

                } else if (status == 2) {

                    Intent Intent1 = new Intent(Splash.this, FindLocation.class);
                    startActivity(Intent1);


                  /*  Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);*/
                   /* Intent mainIntent = new Intent(Splash.this, FindLocation.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
*/
                } else if (status == 3) {
                    Intent mainIntent = new Intent(Splash.this, RegisterActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();

                } else if (status == 4) {
                    Intent mainIntent = new Intent(Splash.this, RegisterActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();

                } else if (status == 5) {
                    Intent mainIntent = new Intent(Splash.this, RegisterActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();

                } else {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                /* Create an Intent that will start the Menu-Activity. */

/*



                if (device_imei.equals(admin_imei)) {
                    Intent mainIntent = new Intent(Splash.this, AdminControl.class);
                    //Intent mainIntent = new Intent(Splash.this, GoogleMapActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                } else if ((user_id == null)) {

                    *//*Intent mainIntent = new Intent(Splash.this, RegisterActivity.class);
                    //Intent mainIntent = new Intent(Splash.this, GoogleMapActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();*//*

                } else {


                   *//* //Intent mainIntent = new Intent(Splash.this, GoogleMapActivity.class);
                    Intent mainIntent = new Intent(Splash.this, FindLocation.class);
                    //mainIntent.putExtra("user_id_intent", user_id);
                    //mainIntent.putExtra("user_name_intent", user_name);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();*//*
                    Toast.makeText(getApplicationContext(), "Thanx", Toast.LENGTH_LONG).show();

                }*/
            }
        }, 2000);


    }


    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }

    private void requestReadPhoneStatePermission() {
/*
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(LoginForm.this)
                    .setTitle("Permission Request")
                    .setMessage(getString(R.string.permission_rationale))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(LoginForm.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .setIcon(R.drawable.exit1)
                    .show();
        } else {
*/
        // READ_PHONE_STATE permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//        }
    }

    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        serialnumber = simCardInfoBean.setSimID(tm.getSimSerialNumber());
        operatorname = simCardInfoBean.setSimCompanyName(tm.getSimOperatorName());
        imeinumber = simCardInfoBean.setPhoneEMINo(tm.getDeviceId());

        Log.e("Marsh-getSimID:", serialnumber);
        Log.e("Marsh-getSimCompany:", operatorname);
        Log.e("Marsh-getPhoneEMINo:", imeinumber);

    }

    public void checkimei() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface service1 = retrofit.create(RequestInterface.class);
        Call<List<UserStatus>> call1 = service1.sendImei(serialnumber, imeinumber);
        call1.enqueue(new Callback<List<UserStatus>>() {
            @Override
            public void onResponse(Call<List<UserStatus>> call, Response<List<UserStatus>> response) {

                userStatuses = response.body();
                if (userStatuses == null) {

                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();


                } else {

                    Log.e("inside-splash", userStatuses.toString());
                    status = (userStatuses.get(0).getSuccess());
                    user_id = String.valueOf((userStatuses.get(0).getUser_id()));
                    user_name = (userStatuses.get(0).getUser_name());
                    Log.e("inside_success", status + "");
                    Log.e("inside_success", user_id + "");
                    Log.e("inside_success", user_name + "");


                    sharedPreferences = getSharedPreferences("MyPrefs", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userID", user_id);
                    editor.putString("username", user_name);
                    //editor.apply();
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<List<UserStatus>> call, Throwable t) {
                Log.e("problemm", t.toString());
                Toast.makeText(getApplicationContext(), " Connection Not Availbale", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void checkuser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface service1 = retrofit.create(RequestInterface.class);
        Call<List<CheckUserBean>> call1 = service1.checkuser();
        call1.enqueue(new Callback<List<CheckUserBean>>() {
            @Override
            public void onResponse(Call<List<CheckUserBean>> call, Response<List<CheckUserBean>> response) {

                checkUserBeen = response.body();

                Log.e("inside-splash", checkUserBeen.toString());
                role = Integer.parseInt((checkUserBeen.get(0).getUser_role()));
                //imeinumber= checkUserBeen.get(0).getUser_imei();
                Log.i("inside_role", role + "");


            }

            @Override
            public void onFailure(Call<List<CheckUserBean>> call, Throwable t) {
                Log.e("problemm", t.toString());
            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivitymanager = (ConnectivityManager) getApplicationContext().getSystemService("connectivity");
        if (connectivitymanager != null) {
            NetworkInfo anetworkinfo[] = connectivitymanager.getAllNetworkInfo();
            if (anetworkinfo != null) {
                for (int i = 0; i < anetworkinfo.length; i++) {
                    if (anetworkinfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
