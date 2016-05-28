package co.haptik.test.chatstat;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public interface HomeContract {

    interface View {
        void showContent();
        void showProgress(boolean show);
        void showMessage(String message);
        void showError(String message, boolean showRetry);
    }

    interface Presenter {
        void loadContent(boolean forceRefresh);
        void cleanUp();
    }

}
