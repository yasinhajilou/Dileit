package com.example.dileit.reciever;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.dileit.R;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.repository.InternalRepository;
import com.example.dileit.view.activity.ReviewLeitnerActivity;
import com.example.dileit.viewmodel.InternalViewModel;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AlarmReceiver extends BroadcastReceiver {

    private String NOTIFICATION_CHANNEL_ID = "Leitner_Review_Reminder";
    private int NOTIFICATION_ID = 999;
    private InternalRepository mInternalRepository;
    int todayCardSize;
    private String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        mInternalRepository = new InternalRepository(context);

        mInternalRepository.getTodayListSizeSingle().subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(Integer integer) {
                if (integer > 0) {
                    todayCardSize = integer;
                    NotificationManager notificationManager = (NotificationManager)
                            context.getSystemService(Context.NOTIFICATION_SERVICE);

                    if (Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                                context.getString(R.string.leitner_review_reminder), NotificationManager.IMPORTANCE_HIGH);
                        notificationChannel.setDescription(context.getString(R.string.reminder_descriotion));
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.enableVibration(true);
                        notificationChannel.shouldVibrate();
                        notificationManager.createNotificationChannel(notificationChannel);
                    }

                    Intent intentLeitnerReviewing = new Intent(context, ReviewLeitnerActivity.class);
                    String safeVerb = todayCardSize > 1 ? context.getString(R.string.are) : context.getString(R.string.is) ;
                    String safeNoun = todayCardSize > 1 ? context.getString(R.string.cards) : context.getString(R.string.card);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                                    .setPriority(NotificationManager.IMPORTANCE_MAX)
                                    .setAutoCancel(true)
                                    .setContentIntent(PendingIntent.getActivity(
                                            context, NOTIFICATION_ID, intentLeitnerReviewing, PendingIntent.FLAG_UPDATE_CURRENT))
                                    .setContentText(context.getString(R.string.there) + safeVerb + todayCardSize + " "+context.getString(R.string.leitner)+" " + safeNoun + " "+ context.getString(R.string.for_reviwing))
                                    .setContentTitle(context.getString(R.string.review_today_cards))
                                    .setSmallIcon(R.drawable.ic_leitner_reminder);

                    notificationManager.notify(NOTIFICATION_ID, builder.build());

                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });


    }
}
