package uk.co.ribot.projectbandit.ui.signin;

import uk.co.ribot.projectbandit.ui.base.MvpView;

/**
 * Created by klemeilleur on 11/18/2015.
 */
public interface SignInMvpView extends MvpView {

    void showSignInSuccessful();

    void showSignInInvalid();

    void showConnectivityFailure(String errorMessage);
}
