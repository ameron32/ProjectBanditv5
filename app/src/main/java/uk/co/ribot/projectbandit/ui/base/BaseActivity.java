package uk.co.ribot.projectbandit.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.ribot.projectbandit.BoilerplateApplication;
import uk.co.ribot.projectbandit.injection.component.ActivityComponent;
import uk.co.ribot.projectbandit.injection.component.DaggerActivityComponent;
import uk.co.ribot.projectbandit.injection.module.PresentersModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(BoilerplateApplication.get(this).getComponent())
                    .presentersModule(new PresentersModule(this))
                    .build();
        }
        return mActivityComponent;
    }

}
