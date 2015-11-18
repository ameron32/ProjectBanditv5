package uk.co.ribot.projectbandit.injection.component;

import dagger.Component;
import uk.co.ribot.projectbandit.data.DataManager;
import uk.co.ribot.projectbandit.injection.module.DataManagerModule;
import uk.co.ribot.projectbandit.injection.scope.PerDataManager;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent {

    void inject(DataManager dataManager);
}
