package co.haptik.test.chatstat.model.source.remote;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import rx.Observable;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public interface MessageServiceApi {

    Observable<List<Message>> getMessages();

}
