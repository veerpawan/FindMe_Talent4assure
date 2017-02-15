package pawan.t4a.com.onlinetracing.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pawan.t4a.com.onlinetracing.Interface.RequestInterface;
import pawan.t4a.com.onlinetracing.Models.UserSuccessBean;
import pawan.t4a.com.onlinetracing.Models.getOtpBean;
import pawan.t4a.com.onlinetracing.R;
import pawan.t4a.com.onlinetracing.Models.SimCardInfoBean;
import pawan.t4a.com.onlinetracing.constant.URL_Mapping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity {
    String serialnumber = null, operatorname = null, imeinumber = null;


    SimCardInfoBean simCardInfoBean = new SimCardInfoBean();
    EditText et_fname, et_mname, et_lname, et_email, et_password, et_mobile, et_dob;
    Button btn_register, btn_cancel;
    String str_f_name = null, str_m_name = null, str_l_name = null, str_email = null, str_password = null, str_mobile = null, str_dob = null;
    List<getOtpBean> getOtpBeen;
    Toolbar toolbar;
    AwesomeValidation awesomeValidation;
    SharedPreferences sharedPreferences;
    String user_id, user_name;
    String user_otp;
    String m_Text = "";
    ProgressBar pbar;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
      /*  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)) {
            loadIMEI();
        } else {
            ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_PHONE_STATE);
            ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.INTERNET);
            ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE);
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            serialnumber = simCardInfoBean.setSimID(tm.getSimSerialNumber());
            operatorname = simCardInfoBean.setSimCompanyName(tm.getSimOperatorName());
            imeinumber = simCardInfoBean.setPhoneEMINo(tm.getDeviceId());
            Log.e("noMarsh-getSimID:", serialnumber);
            Log.e("noMarsh-getSimCompany:", operatorname);
            Log.e("noMarsh-getPhoneEMINo:", imeinumber);
        }


        et_fname = (EditText) findViewById(R.id.et_fname);
        et_mname = (EditText) findViewById(R.id.et_mname);
        et_lname = (EditText) findViewById(R.id.et_lname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        et_dob = (EditText) findViewById(R.id.et_dob);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.et_fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.et_phone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.et_dob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                str_f_name = et_fname.getText().toString().trim();
                str_m_name = et_mname.getText().toString().trim();
                str_l_name = et_lname.getText().toString().trim();
                str_email = et_email.getText().toString().trim();
                str_mobile = et_mobile.getText().toString().trim();
                str_password = et_password.getText().toString().trim();
                str_dob = et_dob.getText().toString().trim();


                if (awesomeValidation.validate()) {


                    pDialog = new ProgressDialog(RegisterActivity.this);
                    pDialog.setMessage("Validating....Please Wait");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();

                    register_user();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_fname.setText("");
                et_mname.setText("");
                et_lname.setText("");
                et_email.setText("");
                et_mobile.setText("");
                et_password.setText("");
                et_dob.setText("");

            }
        });
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
        Log.e("Marsh-getSimID_r:", serialnumber);
        Log.e("Marsh-getSimCompany_r:", operatorname);
        Log.e("Marsh-getPhoneEMINo_r:", imeinumber);


    }

    public void initviews() {


    }

    public void register_user() {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(150, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        RequestInterface service1 = retrofit.create(RequestInterface.class);


        Call<List<getOtpBean>> call1 = service1.get_otp(str_mobile);

      /*  Call<List<UserSuccessBean>> call1 = service1.reg_user(
                str_f_name,
                str_m_name,
                str_l_name,
                str_email,
                str_mobile,
                str_password,
                str_dob,
                serialnumber,
                operatorname,
                imeinumber);*/

        call1.enqueue(new Callback<List<getOtpBean>>() {
            @Override
            public void onResponse(Call<List<getOtpBean>> call, Response<List<getOtpBean>> response) {

                getOtpBeen = response.body();
                pDialog.hide();
                int success_msg = getOtpBeen.get(0).getSuccess();

                //Log.e("uuuser__id", user_id);


                if (getOtpBeen.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorredprimary));
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (success_msg == 9) {

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Mobile already Registerd!!!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorredprimary));
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();

                } else if (success_msg == 1) {

                    user_otp = getOtpBeen.get(0).getUser_otp();
                    Log.e("inside_otpreg", user_otp);
                    Intent i = new Intent(getApplicationContext(), OTP_Screen.class);
                    i.putExtra("user_otp", user_otp);
                    i.putExtra("user_fname", str_f_name);
                    i.putExtra("user_mname", str_m_name);
                    i.putExtra("user_lname", str_l_name);
                    i.putExtra("user_email", str_email);
                    i.putExtra("user_mobile", str_mobile);
                    i.putExtra("user_password", str_password);
                    i.putExtra("user_dob", str_dob);
                    i.putExtra("user_serialnumber", serialnumber);
                    i.putExtra("user_operatorname", operatorname);
                    i.putExtra("user_imeinumber", imeinumber);
                    startActivity(i);
                    finish();






                  /*  startActivity(new Intent(getApplicationContext(), OTP_Screen.class));
                    getIntent().putExtra("user_otp", user_otp);*/
                    //getIntent().putExtra("user_name_intent", user_name);
/*

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Please Enter otp");

                    // Set up the input
                    final EditText input = new EditText(RegisterActivity.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();

                            if (m_Text.equals(user_otp)) {


                                sharedPreferences = getSharedPreferences("MyPrefs", 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userID", user_id);
                                editor.putString("username", user_name);
                                //editor.apply();
                                editor.commit();


                                snackbartext("Successfully Registered");

                                Intent serviceIntent = new Intent(RegisterActivity.this, MyService.class);
                                startService(serviceIntent);

                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
*/


                    //Toast.makeText(getApplicationContext(), "Thanx", Toast.LENGTH_LONG).show();

                  /*  Intent i = new Intent(getApplicationContext(), FindLocation.class);
                    startActivity(i);
                    finish();*/

                    //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    //getIntent().putExtra("user_id_intent", user_id);
                    //getIntent().putExtra("user_name_intent", user_name);

                } else {


                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorredprimary));
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();


                }

            }

            @Override
            public void onFailure(Call<List<getOtpBean>> call, Throwable t) {
                Log.e("problemm", t.toString());


            }
        });


    }

    public void snackbartext(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorredprimary));
        textView.setTextColor(Color.WHITE);
        snackbar.show();


    }
}
