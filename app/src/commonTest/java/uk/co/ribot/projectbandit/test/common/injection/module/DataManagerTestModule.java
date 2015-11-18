package uk.co.ribot.projectbandit.test.common.injection.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.projectbandit.data.local.DatabaseHelper;
import uk.co.ribot.projectbandit.data.local.PreferencesHelper;
import uk.co.ribot.projectbandit.data.remote.RibotsService;
import uk.co.ribot.projectbandit.injection.scope.PerDataManager;

import static org.mockito.Mockito.mock;

/**
 * Provides dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary
 */
@Module
public class DataManagerTestModule {

    private final Context mContext;

    public DataManagerTestModule(Context context) {
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
        return mock(RibotsService.class);
    }
}
