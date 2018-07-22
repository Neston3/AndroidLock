package com.example.maricajr.androidlock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int RESULT_ENABLE = 11;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName compName;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        compName = new ComponentName(getApplicationContext(), Administrator.class);
        keyguardManager= (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);

        boolean active = devicePolicyManager.isAdminActive(compName);

        if (active){

            Intent data=getIntent();
            String value=data.getStringExtra("unlock");
            if (value != null){
               //unlocked do maricajr
            }else {
                lockDevice();
            }

        }else {
            enableLockAdminPermission();
        }

    }

    private void enableLockAdminPermission() {

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Additional text explaining why we need this permission");
        startActivityForResult(intent, RESULT_ENABLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {

            case RESULT_ENABLE :
                if (resultCode == Activity.RESULT_OK) {
                    lockDevice();

                } else {
                    super.finish();
                    Toast.makeText(MainActivity.this,
                            "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    private void lockDevice() {
        devicePolicyManager.lockNow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (keyguardManager.isDeviceLocked()){
                finish();
            }
        }
        finish();
    }

}
