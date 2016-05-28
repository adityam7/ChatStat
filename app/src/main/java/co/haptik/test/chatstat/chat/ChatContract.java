package co.haptik.test.chatstat.chat;

import java.util.List;

import co.haptik.test.chatstat.model.Message;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public interface ChatContract {
    interface View {
        void showMessages(List<Message> messages);
        void showProgress(boolean show);
        void showMessage(String message);
        void showError(String message, boolean showRetry);
    }

    interface Presenter {
        void loadMessages(boolean forceRefresh);
        void cleanUp();
    }
}
