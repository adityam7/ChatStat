package co.haptik.test.chatstat.model.source.local;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import rx.Observable;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public interface MessagePersistenceApi {

    Observable<List<Message>> getFreshMessages();

    Observable<List<Message>> getMessages();

    void saveMessages(List<Message> messages);

    boolean updateMessage(Message message);

}
