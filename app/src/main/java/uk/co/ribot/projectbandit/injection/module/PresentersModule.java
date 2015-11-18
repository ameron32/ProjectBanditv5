package uk.co.ribot.projectbandit.injection.module;

import android.content.Context;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.projectbandit.injection.scope.PerActivity;
import uk.co.ribot.projectbandit.ui.main.MainPresenter;
import uk.co.ribot.projectbandit.ui.signin.SignInPresenter;

/**
 * This module provides instances of Presenters.
 */
@Module
public class PresentersModule {

    private WeakReference<Context> mContextWeakRef;

    public PresentersModule(Context context) {
        mContextWeakRef = new WeakReference<>(context);
    }

    @Provides
    @PerActivity
    MainPresenter providesMainPresenter() {
        return new MainPresenter(mContextWeakRef.get());
    }

    @Provides
    @PerActivity
    SignInPresenter providesSignInPresenter() {
        return new SignInPresenter(mContextWeakRef.get());
    }

}
