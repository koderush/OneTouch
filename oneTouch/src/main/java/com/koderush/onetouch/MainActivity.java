package com.koderush.onetouch;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity {

    private static final String MOBILE_NUMBER = "+13063063006";

    private static final String[] MESSAGES = {
            "message 1",
            "message 2",
            "message 3",
            "message 4",
            "message 5",
            "message 6"};

    private int m_messageIndex = 0;

    private static final String VERSION = "2.6.0";

    private static final int MY_PERMISSIONS_REQUEST = 1000;

    private TextView m_infoView;

    private boolean m_permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_infoView = findViewById(R.id.textViewInfo);

        TextView versionView = findViewById(R.id.textViewVersion);
        versionView.setText(VERSION);

        requestPermission();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_testing) {
            int messageId = m_messageIndex % MESSAGES.length;

            m_infoView.setText(MESSAGES[messageId] + " is sent to galaxy");

            m_messageIndex++;

            return true;
        } else if (id == R.id.action_settings) {
            m_infoView.setText("Not implemented yet.");
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickButton_0_0(View v) {
        sendSMSMessage(0);
    }

    public void onClickButton_0_1(View v) {
        sendSMSMessage(1);
    }

    public void onClickButton_1_0(View v) {
        sendSMSMessage(2);
    }

    public void onClickButton_1_1(View v) {
        sendSMSMessage(3);
    }

    public void onClickButton_2_0(View v) {
        sendSMSMessage(4);
    }

    public void onClickButton_2_1(View v) {
        sendSMSMessage(5);
    }

    private void sendSMSMessage(int messageIndex) {

        if (!m_permissionGranted) {
            m_infoView.setText("SEND_SMS permission has not been granted.");

            return;
        }
        TextView infoView = findViewById(R.id.textViewInfo);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String currentTime = df.format(cal.getTime());

        String message = MESSAGES[messageIndex] + "[" + currentTime + "]";

        infoView.setText("Sending:\"" + message + "\" to:" + MOBILE_NUMBER);

        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(MOBILE_NUMBER, null, message, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            m_infoView.setText("Some permission was not granted.");
            m_permissionGranted = false;

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST);
        } else {
            m_infoView.setText("Permissions were granted.");
            m_permissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    m_infoView.setText("Permissions were granted.");;

                    m_permissionGranted = true;
                } else {
                    m_infoView.setText("Some permission was not granted.");

                    m_permissionGranted = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
