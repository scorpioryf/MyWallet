package com.testdemo.holyg.mywallet;


import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "gryphon";


    private BiometricPrompt mBiometricPrompt;
    private CancellationSignal mCancellationSignal;
    private BiometricPrompt.AuthenticationCallback mAuthenticationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageView imageView = findViewById(R.id.imageView);

        final Intent intent = new Intent();
        intent.setClass(this,MainAccountActivity.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBiometricPrompt = new BiometricPrompt.Builder(MainActivity.this)
                        .setTitle("Check your Fingerprint.")
                        .setDescription("Confirm to login")
                        .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i(TAG, "Cancel button clicked");
                            }
                        })
                        .build();

                mCancellationSignal = new CancellationSignal();
                mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        //handle cancel result
                        Log.i(TAG, "Canceled");
                    }
                });

                mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);

                        Log.i(TAG, "onAuthenticationError " + errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        Log.i(TAG, "onAuthenticationSucceeded " + result.toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();

                        Log.i(TAG, "onAuthenticationFailed ");
                    }
                };

                mBiometricPrompt.authenticate(mCancellationSignal, getMainExecutor(), mAuthenticationCallback);

            }
        });

    }
}
