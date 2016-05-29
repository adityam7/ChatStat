package co.haptik.test.chatstat.stat;

import java.util.List;

import co.haptik.test.chatstat.model.Stat;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public interface StatContract {
    interface View {
        void showStats(List<Stat> stats);
        void showProgress(boolean show);
        void showMessage(String message);
        void showError(String message, boolean showRetry);
    }

    interface Presenter {
        void loadStats(boolean forceRefresh);
        void cleanUp();
    }
}
