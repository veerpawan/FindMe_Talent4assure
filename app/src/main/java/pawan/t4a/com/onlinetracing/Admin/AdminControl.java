package pawan.t4a.com.onlinetracing.Admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;

import pawan.t4a.com.onlinetracing.Interface.RequestInterface;
import pawan.t4a.com.onlinetracing.Models.CurrentUserLocationBean;
import pawan.t4a.com.onlinetracing.Models.UserDetailBean;
import pawan.t4a.com.onlinetracing.R;
import pawan.t4a.com.onlinetracing.constant.URL_Mapping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pawan on 1/27/2017.
 */

public class AdminControl extends AppCompatActivity implements
        View.OnClickListener {


    List<UserDetailBean> userDetailBeen;
    String[] user_name, user_email, user_id;
    double[] user_longitude, user_latitude;
    Spinner sp_user_email;
    String selected_name, selected_email, selected_id;
    Button btn_submit;
    SharedPreferences sharedPreferences;
    Button btnDatePicker, btnTimePicker, btntodatepickecr, btntotimepickecr;
    EditText txtDate, txtTime, txttodate, txttotime;
    int mYear, mMonth, mDay, mHour, mMinute, nYear, nMonth, nDay, nHour, nMinute;
    String selected_date, selected_time, selected_to_date, selected_to_time;
    private List<CurrentUserLocationBean> currentUserLocationBeen;
    String frmdate;
    String todate;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        sp_user_email = (Spinner) findViewById(R.id.spnr_email);
        btn_submit = (Button) findViewById(R.id.btn_logout);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);


        btntodatepickecr = (Button) findViewById(R.id.btn_to_date);
        btntotimepickecr = (Button) findViewById(R.id.btn_to_time);


        txtDate = (EditText) findViewById(R.id.frm_date);
        txtTime = (EditText) findViewById(R.id.frm_time);

        txttodate = (EditText) findViewById(R.id.to_date);
        txttotime = (EditText) findViewById(R.id.to_time);

        btntodatepickecr.setOnClickListener(this);
        btntotimepickecr.setOnClickListener(this);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        get_userdetails();


        sp_user_email.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Toast.makeText(parent.getContext(), "" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
                selected_name = user_name[pos];
                selected_email = user_email[pos];
                selected_id = user_id[pos];
                //get_state(selected_countryid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((selected_date == null) && (selected_to_date == null) && (selected_id == null)) {

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Fields cannot be empty ", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                } else {
                    frmdate = selected_date + " " + selected_time;
                    todate = selected_to_date + " " + selected_to_time;


                    Log.e("inside_submit", selected_id);
                    Log.e("inside_submit", frmdate);
                    Log.e("inside_submit", todate);

                    submit_user_details(selected_id, frmdate, todate);
                }
            }
        });

    }

    public void getuserdetailsadapter() {


        user_name = new String[userDetailBeen.size()];
        user_email = new String[userDetailBeen.size()];
        user_id = new String[userDetailBeen.size()];


        for (int i = 0; i < userDetailBeen.size(); i++) {

            user_name[i] = userDetailBeen.get(i).getUser_name();
            user_email[i] = userDetailBeen.get(i).getUser_email();
            user_id[i] = userDetailBeen.get(i).getUser_id();


        }


        ArrayAdapter emailadapter = new ArrayAdapter(AdminControl.this, android.R.layout.simple_list_item_1, user_email);

        emailadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_user_email.setAdapter(emailadapter);
    }

    public void get_userdetails() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface service = retrofit.create(RequestInterface.class);
        Call<List<UserDetailBean>> call = service.get_userdetails();
        call.enqueue(new Callback<List<UserDetailBean>>() {
            @Override
            public void onResponse(Call<List<UserDetailBean>> call, Response<List<UserDetailBean>> response) {

                userDetailBeen = response.body();
                //Log.e("uu_beann", userDetailBeen.toString());
                if (userDetailBeen == null) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No user found ", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                } else {

                    getuserdetailsadapter();
                }


            }

            @Override
            public void onFailure(Call<List<UserDetailBean>> call, Throwable t) {
            }
        });


    }


    @Override
    public void onClick(View view) {


        if (view == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            selected_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == btnTimePicker) {

            // Get Current Time
            Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);


            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            selected_time = hourOfDay + ":" + minute;

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }


        if (view == btntodatepickecr) {

            // Get Current Date
            Calendar c = Calendar.getInstance();
            nYear = c.get(Calendar.YEAR);
            nMonth = c.get(Calendar.MONTH);
            nDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            selected_to_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            txttodate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == btntotimepickecr) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            nHour = c.get(Calendar.HOUR_OF_DAY);
            nMinute = c.get(Calendar.MINUTE);


            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            selected_to_time = hourOfDay + ":" + minute;

                            txttotime.setText(hourOfDay + ":" + minute);
                        }
                    }, nHour, nMinute, false);
            timePickerDialog.show();
        }
    }

    public void submit_user_details(String s_selected_id, String frm_date, String to_date) {

        Log.e("inside_retr", s_selected_id);
        Log.e("inside_retr", frm_date);
        Log.e("inside_retr", to_date);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface service = retrofit.create(RequestInterface.class);
        Call<List<CurrentUserLocationBean>> call = service.send_userdetails(s_selected_id, frm_date, to_date);
        call.enqueue(new Callback<List<CurrentUserLocationBean>>() {
            @Override
            public void onResponse(Call<List<CurrentUserLocationBean>> call, Response<List<CurrentUserLocationBean>> response) {

                currentUserLocationBeen = response.body();
                Log.e("bbe", currentUserLocationBeen.toString());
                if (currentUserLocationBeen.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No location found ", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                } else {


                    if ((currentUserLocationBeen.get(0).getLatitude() == null) && (currentUserLocationBeen.get(0).getLongitude() == null)) {

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No location found ", Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                    } else {

                        user_latitude = new double[currentUserLocationBeen.size()];

                        user_longitude = new double[currentUserLocationBeen.size()];


                        for (int i = 0; i < currentUserLocationBeen.size(); i++) {

                            user_latitude[i] = currentUserLocationBeen.get(i).getLatitude();
                            user_longitude[i] = currentUserLocationBeen.get(i).getLongitude();


                        }

                        Intent i = new Intent(getApplicationContext(), RouteMap.class);

                        i.putExtra("u_lat", user_latitude);
                        i.putExtra("u_long", user_longitude);
                        startActivity(i);
                    }

                }

            }

            @Override
            public void onFailure(Call<List<CurrentUserLocationBean>> call, Throwable t) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_findlocation, menu);
        menu.getItem(0).setIcon(R.drawable.option_icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;
        switch (item.getItemId()) {
            case R.id.action_refresh:
               /* i = new Intent(getApplicationContext(), FindLocation.class);
                startActivity(i);
                finish();*/
                break;
            case R.id.action_settings:

                break;
            case R.id.action_logout:


                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminControl.this);
                Log.d("Debug", "We have created alert");
                builder.setMessage("Are you sure you want to logout ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i) {

                        sharedPreferences = getSharedPreferences("MyPrefs", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        /*editor.remove("userID");
                        editor.remove("username");*/
                        editor.clear();
                        //editor.apply();
                        editor.commit();
                        //h.removeCallbacks(runnable);
                        //FindLocation.this.finish();

                      /*  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
*/

                    }


                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //FindLocation.this.finish();
                        //h.removeCallbacks(runnable);

                    }
                }).setNegativeButton("no", null).show();
    }
}

