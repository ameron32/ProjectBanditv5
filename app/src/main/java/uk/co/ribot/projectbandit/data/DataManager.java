package uk.co.ribot.projectbandit.data;

import com.squareup.otto.Bus;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import uk.co.ribot.projectbandit.BoilerplateApplication;
import uk.co.ribot.projectbandit.data.local.DatabaseHelper;
import uk.co.ribot.projectbandit.data.local.PreferencesHelper;
import uk.co.ribot.projectbandit.data.model.Ribot;
import uk.co.ribot.projectbandit.data.remote.RibotsService;
import uk.co.ribot.projectbandit.injection.component.DaggerDataManagerComponent;
import uk.co.ribot.projectbandit.injection.module.DataManagerModule;

public class DataManager {

    @Inject
    protected RibotsService mRibotsService;
    @Inject
    protected DatabaseHelper mDatabaseHelper;
    @Inject
    protected PreferencesHelper mPreferencesHelper;
    @Inject
    protected Bus mBus;

    public DataManager(Context context) {
        injectDependencies(context);
    }

    protected void injectDependencies(Context context) {
        DaggerDataManagerComponent.builder()
                .applicationComponent(BoilerplateApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule(context))
                .build()
                .inject(this);
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

    public Observable<String> getUsername() {
        return mDatabaseHelper.getUsername();
    }
}
