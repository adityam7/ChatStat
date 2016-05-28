package co.haptik.test.chatstat.model.source;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import rx.Observable;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public interface MessageDataManagerApi {

    Observable<List<Message>> loadMessages(boolean forceRefresh);

    void updateMessage(Message message);

    boolean isNetworkAvailable();

}
