package uk.co.ribot.projectbandit.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.projectbandit.injection.component.ApplicationComponent;
import uk.co.ribot.projectbandit.test.common.injection.module.ApplicationTestModule;
import uk.co.ribot.projectbandit.test.common.injection.module.DefaultSchedulersTestModule;

@Singleton
@Component(modules = {ApplicationTestModule.class, DefaultSchedulersTestModule.class})
public interface TestComponent extends ApplicationComponent {

}
