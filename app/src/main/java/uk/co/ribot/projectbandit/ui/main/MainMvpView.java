package uk.co.ribot.projectbandit.ui.main;

import java.util.List;

import uk.co.ribot.projectbandit.data.model.Ribot;
import uk.co.ribot.projectbandit.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError(String errorMessage);

}
