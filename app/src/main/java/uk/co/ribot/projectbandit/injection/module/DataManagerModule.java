package uk.co.ribot.projectbandit.injection.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.projectbandit.data.local.DatabaseHelper;
import uk.co.ribot.projectbandit.data.local.PreferencesHelper;
import uk.co.ribot.projectbandit.data.remote.RibotsService;
import uk.co.ribot.projectbandit.injection.scope.PerDataManager;

/**
 * Provide dependencies to the DataManager, mainly Helper classes and Retrofit services.
 */
@Module
public class DataManagerModule {

    private final Context mContext;

    public DataManagerModule(Context context) {
        mContext = context;
    }

    @Provides
    @PerDataManager
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(mContext);
    }

    @Provides
    @PerDataManager
    PreferencesHelper providePreferencesHelper() {
        return new PreferencesHelper(mContext);
    }

    @Provides
    @PerDataManager
    RibotsService provideRibotsService() {
        return RibotsService.Creator.newRibotsService();
    }
}
