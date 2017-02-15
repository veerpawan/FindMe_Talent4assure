package pawan.t4a.com.onlinetracing.Activity;

import android.app.Application;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import pawan.t4a.com.onlinetracing.Activity.MyService;

/**
 * Created by ARUN on 2/1/2017.
 */

public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate()
    {
      startService(new Intent(this, MyService.class));
        //startService(new Intent(this, MyIntentService.class));
        super.onCreate();
    }
}
