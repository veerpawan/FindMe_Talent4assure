package pawan.t4a.com.onlinetracing.Activity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pawan.t4a.com.onlinetracing.Interface.RequestInterface;
import pawan.t4a.com.onlinetracing.Models.StatusBean;
import pawan.t4a.com.onlinetracing.R;
import pawan.t4a.com.onlinetracing.constant.URL_Mapping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService extends Service {


    TextView tvLocation;
    GPSTracker gps;
    String locationAddress;
    List<StatusBean> statusBeen;
    ProgressBar loader;
    double latitude;
    double longitude;
    String user_id, user_name;
    int user_role;
    SharedPreferences shared;
    Handler h = new Handler();
    Runnable runnable;

    int delay = 10000;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        shared = getSharedPreferences("MyPrefs", 0);
        user_id = shared.getString("userID", null);
        Log.e("ing", user_id);

        Log.i("Tag", "inside onStartCommand");
        Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
        getLocation();


       /* runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Fetching Location", Toast.LENGTH_LONG).show();
                //setAddress();
                getLocation();
                h.postDelayed(this, delay);
            }
        };
        h.postDelayed(runnable, delay);*/


        return START_STICKY;
    }

    public void getLocation() {

        Log.i("Tag", "inside getLocation()");
        gps = new GPSTracker(getApplicationContext());
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.i("Tag", "latitude:" + latitude);
            Location location = gps.getLocation();
            if (location != null) {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new MyService.GeocoderHandler());
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
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            setAddress();
            //Log.e("location_outside", locationAddress);


        }

    }

    public void setAddress() {


        Log.i("Tag", "inside aet Address");


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
            }

            @Override
            public void onFailure(Call<List<StatusBean>> call, Throwable t) {
                Log.i("Tag", "inside onFailure");
            }
        });


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
       /* Log.i("Tag","inside onTaskRemoved");
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"hello! how r u?",Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(getApplicationContext(), MyService.class);
                //startActivity(i);
            }
        }, 5*1000);*/ // wait for 5 seconds

        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        Log.i("Tag", "after startservice inside onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_LONG).show();
    }


}
