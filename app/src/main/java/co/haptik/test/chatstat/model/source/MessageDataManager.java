package co.haptik.test.chatstat.model.source;

import android.content.Context;

import java.util.List;

import co.haptik.test.chatstat.model.Message;
import co.haptik.test.chatstat.model.source.local.MessagePersistenceApi;
import co.haptik.test.chatstat.model.source.remote.MessageServiceApi;
import co.haptik.test.chatstat.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class MessageDataManager implements MessageDataManagerApi {

    private MessageServiceApi mService;
    private MessagePersistenceApi mPersistence;
    private NetworkUtil mNetworkUtil;

    public MessageDataManager(Context context, MessageServiceApi service, MessagePersistenceApi persistence) {
        mService = service;
        mPersistence = persistence;
        mNetworkUtil = new NetworkUtil(context);
    }

    @Override
    public Observable<List<Message>> loadMessages(boolean forceRefresh) {
        Observable<List<Message>> network;
        if(isNetworkAvailable()) {
            network = mService.getMessages()
                    .doOnNext(new Action1<List<Message>>() {
                        @Override
                        public void call(List<Message> messages) {
                            mPersistence.saveMessages(messages);
                        }
                    });
        } else {
            network = Observable.empty();
        }

        Observable<List<Message>> freshData = mPersistence.getFreshMessages();
        Observable<List<Message>> database = mPersistence.getMessages();

        Observable<List<Message>> source;
        if(isNetworkAvailable() && forceRefresh) {
            source = Observable.concat(network, database).first();
        } else if(!isNetworkAvailable()) {
            source = database;
        } else {
            source = Observable.concat(freshData, network, database).first();
        }

        return source;
    }

    @Override
    public void markFavorite(Message message) {

    }

    @Override
    public boolean isNetworkAvailable() {
        return mNetworkUtil.isNetworkConnected();
    }
}