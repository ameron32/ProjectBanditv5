package uk.co.ribot.projectbandit.injection.component;

import dagger.Component;
import uk.co.ribot.projectbandit.injection.module.PresentersModule;
import uk.co.ribot.projectbandit.injection.scope.PerActivity;
import uk.co.ribot.projectbandit.ui.main.MainActivity;
import uk.co.ribot.projectbandit.ui.signin.SignInActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PresentersModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(SignInActivity signInActivity);
}
