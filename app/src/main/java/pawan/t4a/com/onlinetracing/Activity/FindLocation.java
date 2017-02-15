package pawan.t4a.com.onlinetracing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pawan.t4a.com.onlinetracing.Interface.RequestInterface;
import pawan.t4a.com.onlinetracing.Models.CloseStatus;
import pawan.t4a.com.onlinetracing.Models.StatusBean;
import pawan.t4a.com.onlinetracing.R;
import pawan.t4a.com.onlinetracing.constant.URL_Mapping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.codes;
import static android.R.attr.delay;
import static pawan.t4a.com.onlinetracing.R.layout.splash;

/**
 * Created by pawan on 1/24/2017.
 */

public class FindLocation extends AppCompatActivity implements View.OnClickListener {


    TextView tvLocation;
    Button btnLocation, btn_logout;
    GPSTracker gps;
    String locationAddress;
    SharedPreferences sharedpreferences;
    Timer mTimer1;
    TimerTask mTt1;
    Handler h = new Handler();
    Runnable runnable;
    List<StatusBean> statusBeen;
    List<CloseStatus> closeStatusList;
    int delay = 10000;
    ProgressBar loader;
    SharedPreferences sharedPreferences;
    String user_id, user_name;
    Toolbar toolbar;
    double latitude;
    double longitude;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //btnLocation = (Button) findViewById(R.id.btn_get_location);

        //tvLocation = (TextView) findViewById(R.id.tv_location);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        shared = getSharedPreferences("MyPrefs", 0);
        user_id = shared.getString("userID", null);
        Log.e("ing", user_id);
        Intent intent = getIntent();
      /*  user_id = intent.getStringExtra("user_id_intent");
        user_name = intent.getStringExtra("user_name_intent");*/


        getLocation();
        Toast.makeText(getApplicationContext(), "Fetching Address", Toast.LENGTH_LONG).show();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                //setAddress();

                getLocation();
                Toast.makeText(getApplicationContext(), "Fetching Address", Toast.LENGTH_LONG).show();
                h.postDelayed(this, delay);
            }
        }, delay);


        //btnLocation.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        //getLocation();

    }

    public void getLocation() {

        //loader.setVisibility(View.VISIBLE);
        gps = new GPSTracker(FindLocation.this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Location location = gps.getLocation();
            if (location != null) {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new GeocoderHandler());

            }

            //


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


    }

    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            //locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
           /* Toast.makeText(getApplicationContext(), "Address:"+locationAddress, Toast.LENGTH_LONG).show();*/

            //tvLocation.setText(locationAddress);
            //loader.setVisibility(View.GONE);
            //setAddress(locationAddress);
            setAddress();
            Log.e("location_outside", locationAddress);


        }

    }

    public void setAddress() {


        Log.e("location_inside", locationAddress);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface service1 = retrofit.create(RequestInterface.class);
        Call<List<StatusBean>> call1 = service1.location_of_user(locationAddress, user_id, latitude, longitude);
        call1.enqueue(new Callback<List<StatusBean>>() {
            @Override
            public void onResponse(Call<List<StatusBean>> call, Response<List<StatusBean>> response) {

                statusBeen = response.body();
                int success_msg = statusBeen.get(0).getSuccess();


                if (statusBeen.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong!!!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorredprimary));
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                } else if (success_msg == 1) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Address Submitted Successfully", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();


                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something went wrong!!!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();


                }
            }

            @Override
            public void onFailure(Call<List<StatusBean>> call, Throwable t) {
                Log.e("problemm", t.toString());
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
                i = new Intent(getApplicationContext(), FindLocation.class);
                startActivity(i);
                finish();
                break;
            case R.id.action_settings:


                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Please minimize App.Do not close..Are you sure want to close It?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        sendclosestatus();

                       /* Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/


                    }
                }).setNegativeButton("no", null).show();
    }

    public void sendclosestatus() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_Mapping.userpath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface service1 = retrofit.create(RequestInterface.class);
        Call<List<CloseStatus>> call1 = service1.closestatus(user_id, locationAddress);
        call1.enqueue(new Callback<List<CloseStatus>>() {
            @Override
            public void onResponse(Call<List<CloseStatus>> call, Response<List<CloseStatus>> response) {

                closeStatusList = response.body();
                int success_msg = closeStatusList.get(0).getSuccess();


                if (closeStatusList.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (success_msg == 1) {


                    Toast.makeText(getApplicationContext(), "Application Closed ", Toast.LENGTH_LONG).show();
                    h.removeCallbacks(runnable);
                    FindLocation.this.finish();


                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                } else {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }
            }

            @Override
            public void onFailure(Call<List<CloseStatus>> call, Throwable t) {
                Log.e("problemm", t.toString());
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


}
