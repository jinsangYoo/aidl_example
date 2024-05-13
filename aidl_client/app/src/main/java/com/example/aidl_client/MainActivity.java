package com.example.aidl_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aidl_server.IMyAidlInterface;
import com.example.aidl_server.IServiceStateCallback;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "RemoteMainActivity";

    private TextView switchServerState, tvStateFromCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRemoteService!=null) {
                    try {
                        // update bool value from Server application
                        switchServerState.setText(String.valueOf(mRemoteService.isAvailable()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Service not connected..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchServerState = findViewById(R.id.switchServerState);
        tvStateFromCallback = findViewById(R.id.tvStateFromCallback);
        connectService(); // connect service at onCreate
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectService(); // disconnect service at onDestroy
    }

    private void connectService() {
        Intent i = new Intent();
        i.setAction("oysu.server.service"); // action declared in Server application
        i.setPackage("com.example.aidl_server");
        bindService(i, mConnection, BIND_AUTO_CREATE);
    }

    private void disconnectService(){
        unRegisterCallback();
        unbindService(mConnection);
    }

    private void registerCallback() {
        if(mRemoteService != null){
            try {
                mRemoteService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "mRemoteService is null.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void unRegisterCallback(){
        if(mRemoteService!=null){
            try {
                mRemoteService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private IMyAidlInterface mRemoteService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mRemoteService = IMyAidlInterface.Stub.asInterface(service);
            registerCallback();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mRemoteService = null;
        }
    };

    private IServiceStateCallback mCallback = new IServiceStateCallback.Stub() {
        @Override
        public void makeRectByStatus(CustomRect ranRect) throws RemoteException {
            Log.d(TAG, "makeRectByStatus: " + ranRect.left + ", " + ranRect.top + ", " + ranRect.right + ", " + ranRect.bottom);
        }

        @Override
        public void onServiceStateChanged(final int status) {
            Log.d(TAG, "status:" + status);
            runOnUiThread(new Runnable() { // it comes through non-UI thread, so it need to post it to the Main thread!
                @Override
                public void run() {
                    tvStateFromCallback.setText(String.valueOf(status));
                }
            });
        }
    };
}