package com.example.maricajr.androidlock;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class PhoneLockReceiver extends BroadcastReceiver {

    private KeyguardManager keyguardManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        keyguardManager= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        assert keyguardManager != null;
        if (intent.getAction().equals("android.intent.action.USER_PRESENT")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (keyguardManager.isDeviceLocked()){
                    //locked

                }else {
                    //unlocked
                    Intent intent1=new Intent(context,MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra("unlock","1");
                    context.startActivity(intent1);
                }
            }else {
                //lower API
                if (keyguardManager.inKeyguardRestrictedInputMode()){
                    //locked

                }else {
                    //unlocked
                    Intent intent1=new Intent(context,MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra("unlock","1");
                    context.startActivity(intent1);
                }
            }
        }

    }
}
