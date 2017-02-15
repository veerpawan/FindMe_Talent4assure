package pawan.t4a.com.onlinetracing.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
           /* Intent service = new Intent(context, MyIntentService.class);
            context.startService(service);*/
            Intent service = new Intent(context, MyService.class);
            context.startService(service);
        }


    }
}
