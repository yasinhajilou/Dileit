package com.example.dileit.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

    }
}
