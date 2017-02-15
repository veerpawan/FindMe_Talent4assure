package pawan.t4a.com.onlinetracing.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pawan.t4a.com.onlinetracing.Interface.RequestInterface;
import pawan.t4a.com.onlinetracing.Models.UserSuccessBean;
import pawan.t4a.com.onlinetracing.Models.getOtpBean;
import pawan.t4a.com.onlinetracing.R;
import pawan.t4a.com.onlinetracing.constant.URL_Mapping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OTP_Screen extends AppCompatActivity {
    String user_otp, user_fname, user_mname, user_lname, str_email, str_mobile, str_password, str_dob, serialnumber, operatorname, imeinumber;
    Button btn_reg;
    EditText et_otp;
    String otp_message;
    List<UserSuccessBean> userSuccessBeen;
    ProgressDialog pDialog;
    String user_id, user_name;
    SharedPreferences sharedPreferences;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_otp_screen);
        et_otp = (EditText) findViewById(R.id.otp_message);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        pDialog = new ProgressDialog(OTP_Screen.this);
        pDialog.setMessage("Loading....Please Wait");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.hide();


        Intent intent = getIntent();
        user_otp = intent.getStringExtra("user_otp");
        //Log.e("inside_otpscreen", user_otp);
        user_fname = intent.getStringExtra("user_fname");
        //Log.e("inside_otpscreen", user_fname);
        user_mname = intent.getStringExtra("user_mname");
        //Log.e("inside_otpscreen", user_mname);
        user_lname = intent.getStringExtra("user_lname");
        //Log.e("inside_otpscreen", user_lname);
        str_email = intent.getStringExtra("user_email");
        //Log.e("inside_otpscreen", str_email);
        str_mobile = intent.getStringExtra("user_mobile");
        //Log.e("inside_otpscreen", str_mobile);
        str_password = intent.getStringExtra("user_password");
        //Log.e("inside_otpscreen", str_password);
        str_dob = intent.getStringExtra("user_dob");
        serialnumber = intent.getStringExtra("user_serialnumber");
        //Log.e("inside_otpscreen", serialnumber);
        operatorname = intent.getStringExtra("user_operatorname");
        //Log.e("inside_otpscreen", operatorname);
        imeinumber = intent.getStringExtra("user_imeinumber");
        //Log.e("inside_otpscreen", imeinumber);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_message = et_otp.getText().toString();


                //pDialog.show();
                if (otp_message.isEmpty()) {
                    pDialog.hide();

                    snackbartext("Fields Cannot be Empty");

                } else if (otp_message.equals(user_otp))

                {

                    register_user();
                } else {
                    pDialog.hide();
                    snackbartext("Please Enter Correct OTP");

                }


            }
        });

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


        Call<List<UserSuccessBean>> call1 = service1.reg_user(
                user_fname,
                user_mname,
                user_lname,
                str_email,
                str_mobile,
                str_password,
                str_dob,
                serialnumber,
                operatorname,
                imeinumber);

        call1.enqueue(new Callback<List<UserSuccessBean>>() {
            @Override
            public void onResponse(Call<List<UserSuccessBean>> call, Response<List<UserSuccessBean>> response) {

                userSuccessBeen = response.body();
                pDialog.hide();
                int success_msg = userSuccessBeen.get(0).getSuccess();


                if (userSuccessBeen.isEmpty()) {
                    snackbartext("Something went wrong");
                } else if (success_msg == 1) {

                    et_otp.setText("");


                    user_id = userSuccessBeen.get(0).getUser_id();
                    user_name = userSuccessBeen.get(0).getUser_name();

                    sharedPreferences = getSharedPreferences("MyPrefs", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userID", user_id);
                    editor.putString("username", user_name);
                    //editor.apply();
                    editor.commit();
                    snackbartext("Successfully Registered");


                    Intent i = new Intent(getApplicationContext(), FindLocation.class);
                    startActivity(i);
                    finish();


                } else if (success_msg == 0) {
                    snackbartext("Something went wrong");
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
            public void onFailure(Call<List<UserSuccessBean>> call, Throwable t) {
                Log.e("problemm", t.toString());


            }
        });


    }

    public void snackbartext(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorredprimary));
        textView.setTextColor(Color.YELLOW);
        snackbar.show();


    }

}

