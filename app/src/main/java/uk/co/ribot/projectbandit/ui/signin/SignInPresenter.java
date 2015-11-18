package uk.co.ribot.projectbandit.ui.signin;

import android.content.Context;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;
import uk.co.ribot.projectbandit.BoilerplateApplication;
import uk.co.ribot.projectbandit.R;
import uk.co.ribot.projectbandit.data.DataManager;
import uk.co.ribot.projectbandit.ui.base.BasePresenter;
import uk.co.ribot.projectbandit.util.SchedulerAppliers;

/**
 * Created by klemeilleur on 11/18/2015.
 */
public class SignInPresenter extends BasePresenter<SignInMvpView> {

    @Inject
    protected DataManager mDataManager;
    private Subscription mSubscription;

    public SignInPresenter(Context context) {
        super(context);
    }

    @Override
    public void attachView(SignInMvpView mvpView) {
        super.attachView(mvpView);
        BoilerplateApplication.get(getContext()).getComponent().inject(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void signIn(String username) {
        mSubscription = mDataManager.getUsername()
                .compose(SchedulerAppliers.<String>defaultSchedulers(getContext()))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error signing in.");
                        String errorString = getContext().getString(R.string.error_signing_in);
                        getMvpView().showConnectivityFailure(errorString);
                    }

                    @Override
                    public void onNext(String username) {
                        if (username == null) {
                            getMvpView().showSignInInvalid();
                        } else {
                            // success
                            getMvpView().showSignInSuccessful();
                        }
                    }
                });
    }
}
