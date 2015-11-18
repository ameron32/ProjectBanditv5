package uk.co.ribot.projectbandit.injection.component;

import android.app.Application;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.projectbandit.data.DataManager;
import uk.co.ribot.projectbandit.data.SyncService;
import uk.co.ribot.projectbandit.injection.module.ApplicationModule;
import uk.co.ribot.projectbandit.injection.module.DefaultSchedulersModule;
import uk.co.ribot.projectbandit.ui.main.MainPresenter;
import uk.co.ribot.projectbandit.util.SchedulerApplier;

@Singleton
@Component(modules = {ApplicationModule.class, DefaultSchedulersModule.class})
public interface ApplicationComponent {

    void inject(SyncService syncService);
    void inject(SchedulerApplier.DefaultSchedulers defaultSchedulers);
    void inject(MainPresenter mainPresenter);

    Application application();
    DataManager dataManager();
    Bus eventBus();
}
