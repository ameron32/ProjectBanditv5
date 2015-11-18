package uk.co.ribot.projectbandit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import rx.Observable;
import uk.co.ribot.projectbandit.test.common.TestDataFactory;
import uk.co.ribot.projectbandit.test.common.rules.TestComponentRule;
import uk.co.ribot.projectbandit.ui.signin.SignInMvpView;
import uk.co.ribot.projectbandit.ui.signin.SignInPresenter;
import uk.co.ribot.projectbandit.util.DefaultConfig;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class SignInPresenterTest {

    // We mock the DataManager because there is not need to test the dataManager again
    // from the presenters because there is already a DataManagerTest class.
    @Rule
    public final TestComponentRule component =
            new TestComponentRule((BoilerplateApplication) RuntimeEnvironment.application, true);
    private SignInPresenter mMainPresenter;
    private SignInMvpView mMockMainMvpView;

    @Before
    public void setUp() {
        mMockMainMvpView = mock(SignInMvpView.class);
        mMainPresenter = new SignInPresenter(RuntimeEnvironment.application);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void detachView() {
        mMainPresenter.detachView();
    }

    @Test
    public void signInSucceeds() {
        String username = TestDataFactory.makeUsername();
        doReturn(Observable.just(username))
                .when(component.getDataManager())
                        // TODO replace getRibots()
                .getRibots();
        mMainPresenter.signIn(username);
        verify(mMockMainMvpView).showSignInSuccessful();
        verify(mMockMainMvpView, never()).showSignInInvalid();
        verify(mMockMainMvpView, never()).showConnectivityFailure(anyString());
    }

    @Test
    public void signInInvalid() {
        doReturn(Observable.just(null))
                .when(component.getDataManager())
                        // TODO replace getRibots()
                .getRibots();
        mMainPresenter.signIn(null);
        verify(mMockMainMvpView).showSignInInvalid();
        verify(mMockMainMvpView, never()).showSignInSuccessful();
        verify(mMockMainMvpView, never()).showConnectivityFailure(anyString());
    }

    @Test
    public void signInFails() {
        String username = TestDataFactory.makeUsername();
        doReturn(Observable.error(new RuntimeException()))
                .when(component.getDataManager())
                        // TODO replace getRibots()
                .getRibots();
        mMainPresenter.signIn(username);
        verify(mMockMainMvpView).showConnectivityFailure(anyString());
        verify(mMockMainMvpView, never()).showSignInSuccessful();
        verify(mMockMainMvpView, never()).showSignInInvalid();
    }
}
