package com.example.maricajr.androidlock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final int RESULT_ENABLE = 11;
    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName compName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        compName = new ComponentName(getApplicationContext(), Administrator.class);

        boolean active = devicePolicyManager.isAdminActive(compName);

        if (active){

            Intent data=getIntent();
            String value=data.getStringExtra("unlock");
            if (value != null){
                finish();
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

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void lockDevice() {
        devicePolicyManager.lockNow();
    }

}
