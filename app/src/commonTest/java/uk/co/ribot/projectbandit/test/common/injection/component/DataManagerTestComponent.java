package uk.co.ribot.projectbandit.test.common.injection.component;

import dagger.Component;
import uk.co.ribot.projectbandit.injection.component.DataManagerComponent;
import uk.co.ribot.projectbandit.injection.scope.PerDataManager;
import uk.co.ribot.projectbandit.test.common.injection.module.DataManagerTestModule;

@PerDataManager
@Component(dependencies = TestComponent.class, modules = DataManagerTestModule.class)
public interface DataManagerTestComponent extends DataManagerComponent {
}
