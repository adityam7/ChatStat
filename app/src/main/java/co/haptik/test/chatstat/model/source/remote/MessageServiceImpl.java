package co.haptik.test.chatstat.model.source.remote;

import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.util.HaptikService;
import co.haptik.test.chatstat.util.RetrofitSingleton;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class MessageServiceImpl implements MessageServiceApi {

    private HaptikService mHaptikService;
    private ConnectivityManager mConnectivityManager;

    public MessageServiceImpl() {
        Retrofit retrofit = RetrofitSingleton.getInstance();
        mHaptikService = retrofit.create(HaptikService.class);
    }

    @Override
    public Observable<List<Message>> getMessages() {
        return mHaptikService.getData().map(new Func1<MessageServiceResponse, List<Message>>() {
            @Override
            public List<Message> call(MessageServiceResponse messageServiceResponse) {
                List<Message> messages = messageServiceResponse.getMessages();
                List<Message> result = new ArrayList<Message>(messageServiceResponse.getMessages().size());
                for(Message message : messages) {
                    message.setId(message.getMessageTime().getTime());
                    result.add(message);
                }
                return Message.sortMessages(result);
            }
        });
    }
}
