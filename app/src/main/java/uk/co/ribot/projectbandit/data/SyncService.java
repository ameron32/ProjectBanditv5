package uk.co.ribot.projectbandit.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import timber.log.Timber;
import uk.co.ribot.projectbandit.BoilerplateApplication;
import uk.co.ribot.projectbandit.BuildConfig;
import uk.co.ribot.projectbandit.data.model.Ribot;
import uk.co.ribot.projectbandit.util.AndroidComponentUtil;
import uk.co.ribot.projectbandit.util.NetworkUtil;
import uk.co.ribot.projectbandit.util.SchedulerAppliers;

public class SyncService extends Service {

    @Inject DataManager mDataManager;
    private Subscription mSubscription;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = mDataManager.syncRibots()
                .compose(SchedulerAppliers.<Ribot>defaultSubscribeScheduler(this))
                .subscribe(new Observer<Ribot>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");
                        stopSelf(startId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");
                        stopSelf(startId);

                    }

                    @Override
                    public void onNext(Ribot ribot) {
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtil.isNetworkConnected(context)) {
                if (BuildConfig.DEBUG) {
                    Timber.i("Connection is now available, triggering sync...");
                }
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}